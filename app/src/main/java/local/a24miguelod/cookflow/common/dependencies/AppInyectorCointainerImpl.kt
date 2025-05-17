package local.a24miguelod.cookflow.common.dependencies

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import local.a24miguelod.cookflow.CookFlowApp
import local.a24miguelod.cookflow.data.local.dao.CookFlowDatabase
import local.a24miguelod.cookflow.data.repository.CacheRepository
import local.a24miguelod.cookflow.data.repository.CacheRepositoryRoom
import local.a24miguelod.cookflow.data.repository.RecetasRepository
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


    // Para inyectar FireBase
    // TODO:Aun sin inyectar
    override val firebaseDatabase by lazy {Firebase.firestore }

    //Para inyectar el reposotorio de Recetas
    override val recetasRepository: RecetasRepository by lazy {
        RecetasRepositoryGithub()
    }

    //Para inyectar el repositorio de Cache
    //TODO:Ver esto bien
    override val roomDatabase = Room.databaseBuilder(
        context,
        CookFlowDatabase::class.java, "cookflow-db"
    ).setQueryCallback({ sqlQuery, bindArgs ->
        Log.d("ROOM-SQL", "SQL: $sqlQuery | args: $bindArgs")
    }, Executors.newSingleThreadExecutor()).build()

    private val ingredienteDao by lazy { roomDatabase.ingredienteDao() }
    override val cacheRepository: CacheRepository by lazy {
        CacheRepositoryRoom(ingredienteDao)
    }

}