package local.a24miguelod.cookflow.common.dependencies

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import local.a24miguelod.cookflow.data.local.dao.CookFlowDatabase
import local.a24miguelod.cookflow.data.repository.CacheRepository
import local.a24miguelod.cookflow.data.repository.CacheRepositoryRoom
import local.a24miguelod.cookflow.data.repository.RecetasRepository
import local.a24miguelod.cookflow.data.repository.RecetasRepositoryFirebase
import local.a24miguelod.cookflow.data.repository.RecetasRepositoryGithub
import java.util.concurrent.Executors


/**
 * Implementación del contenedor de inyección de dependencias a nivel de la aplicación.
 * Las variables se inicializan de forma perezosa y la misma instancia se comparte en toda la aplicación.
 */
// Ver tambien https://www.youtube.com/watch?v=eX-y0IEHJjM&t=690s

private const val TAG ="AppInyectorContainerImpl"

class AppInyectorContainerImpl(
    private val context: Context
) : AppInyectorContainer {

    // Para inyectar el repositorioCache
    private val ingredienteDao by lazy { roomDatabase.ingredienteDao() }
    override val cacheRepository: CacheRepository by lazy {
        CacheRepositoryRoom(ingredienteDao)
    }

    // Para inyectar FireBase
    override val firebaseDatabase by lazy {Firebase.firestore }

    //Para inyectar el reposotorio de Recetas
    override val recetasRepository: RecetasRepository by lazy {

        // El repo de firebase necesita el cache porque  las recetas tienen unicamente
        // un uuid al ingrediente.
        // Cacheo los ingrediente para no hacer tantas consultas en cada receta

        // RecetasRepositoryFirebase(cacheRepository)

//        RecetasRepositoryGithub(
//            githubReporecetasUrl = "http://192.168.1.138:8000",
//            githubBaseDir = "http://192.168.1.138:8000/recetas/",
//            httpCliente = httpCliente  // se pasa el cliente inyectado lazy
//        )
//
        RecetasRepositoryGithub(
            githubReporecetasUrl = "https://api.github.com/repos/a24miguelod/recetas/contents/recetas?ref=main",
            githubBaseDir = "https://raw.githubusercontent.com/a24miguelod/recetas/refs/heads/main/recetas/",
            httpCliente = httpCliente  // se pasa el cliente inyectado lazy
        )

    }

    //Para inyectar el cliente http (solo en los ReposGithub)
    override val httpCliente: HttpClient by lazy {
        HttpClient(CIO) {
            // https://ktor.io/docs/client-create-and-configure.html#configure-client
            expectSuccess = true
            install(ContentNegotiation) {
                json(Json {
                    // https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-json/kotlinx.serialization.json/-json-builder/
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    //Para inyectar el repositorio de Cache
    //TODO:Ver esto bien
    override val roomDatabase = Room.databaseBuilder(
        context,
        CookFlowDatabase::class.java, "cookflow-db"
    ).setQueryCallback({ sqlQuery, bindArgs ->
        Log.d("ROOM-SQL", "SQL: $sqlQuery | args: $bindArgs")
    }, Executors.newSingleThreadExecutor()).build()


}