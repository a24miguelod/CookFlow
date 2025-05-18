package local.a24miguelod.cookflow.data.repository


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import local.a24miguelod.cookflow.domain.model.Receta
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.get
import local.a24miguelod.cookflow.data.github.model.DirectoryGithub
import local.a24miguelod.cookflow.utils.parseMarkdownReceta

private const val TAG = "RecetasRepositoryGithub"

class RecetasRepositoryGithub(
    private val githubReporecetasUrl:String,
    private val githubBaseDir: String,
    private val httpCliente: HttpClient

) : RecetasRepository {

    // Obtener la lista de recetas
    override suspend fun getRecetasConFlow(): Flow<List<Receta>> = flow {

        val resultadoParcial = mutableListOf<Receta>()

        // Leo los ficheros del directorio en particular y creo una receta para cada una
        // de las entradas
        val lsDirectory: List<DirectoryGithub> = httpCliente.get(githubReporecetasUrl).body()

        lsDirectory.forEach {
            val recetaFile:String = httpCliente.get(it.downloadUrl!!).body()
            val receta = parseMarkdownReceta(recetaFile, it.name.toString(), )
            resultadoParcial.add(receta)
            emit(resultadoParcial.toList()) // Emitimos una copia
        }

    }

    override suspend fun getReceta(id: String): Receta {
        val recetaFile:String = httpCliente.get("$githubBaseDir$id").body()
        val receta = parseMarkdownReceta(recetaFile,id )

        return receta
    }
}