package local.a24miguelod.cookflow.common.dependencies

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import local.a24miguelod.cookflow.data.repository.RecetasRepository
import local.a24miguelod.cookflow.data.repository.RecetasRepositoryGithub


/**
 * Implementación del contenedor de inyección de dependencias a nivel de la aplicación.
 * Las variables se inicializan de forma perezosa y la misma instancia se comparte en toda la aplicación.
 */
class AppInyectorContainerImpl : AppInyectorContainer {

    /**
     * Usamos el constructor de Retrofit para crear un objeto retrofit usando un
     * convertidor de kotlinx.serialization (en otros ejemplos he usado Gson o Moshi)
     */

    private var db: FirebaseFirestore = Firebase.firestore

    /**
     * ApiFotosRepository es la interfaz que define el contrato para el repositorio de fotos.
     * Implementación de la interfaz FotosRepository.
     */

    // Ver // https://www.youtube.com/watch?v=eX-y0IEHJjM&t=690s

    override val recetasRepository: RecetasRepository by lazy {
        RecetasRepositoryGithub()
    }
}