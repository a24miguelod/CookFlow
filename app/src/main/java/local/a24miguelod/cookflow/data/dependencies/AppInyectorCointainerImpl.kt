package local.a24miguelod.cookflow.data.dependencies

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import local.a24miguelod.cookflow.data.repository.RecetasRepositoryFirebase
import local.a24miguelod.cookflow.data.repository.RecetasRepository
import local.a24miguelod.cookflow.data.repository.RecetasRepositoryGithub


/**
 * Implementación del contenedor de inyección de dependencias a nivel de la aplicación.
 * Las variables se inicializan de forma perezosa y la misma instancia se comparte en toda la aplicación.
 */
class AppInyectorContainerImpl : AppInyectorContainer {

    // TODO:organizar estos comentarios cuando lo tenga claro
    /**
     * Usamos el constructor de Retrofit para crear un objeto retrofit usando un
     * convertidor de kotlinx.serialization (en otros ejemplos he usado Gson o Moshi)
     */

    private var db: FirebaseFirestore = Firebase.firestore

    /**
     * ApiFotosRepository es la interfaz que define el contrato para el repositorio de fotos.
     * Implementación de la interfaz FotosRepository.
     */
    // Instanciación perezosa de ApiFotosRepository. Se crea la instancia cuando se necesita la
    // primera vez y se reutiliza en toda la aplicación.
    override val recetasRepository: RecetasRepository by lazy {
        RecetasRepositoryGithub()
    }
}