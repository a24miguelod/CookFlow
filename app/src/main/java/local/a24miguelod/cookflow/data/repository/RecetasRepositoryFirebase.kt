package local.a24miguelod.cookflow.data.repository

import android.util.Log
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import local.a24miguelod.cookflow.model.Receta

private const val TAG = "RecetasRepositoryFirebase"

class RecetasRepositoryFirebase : RecetasRepository {

    private var db: FirebaseFirestore = Firebase.firestore

    suspend fun getRecetasSinNombreIngredientes(): List<Receta> {

        return try {
            db.collection("recetas")
                .get()
                .await()
                .documents
                .mapNotNull { snapshot ->
                    val receta = snapshot.toObject(Receta::class.java)
                    // Ponemos en receta el identificador de documento de firebase
                    receta?.apply {
                        uuidReceta = snapshot.id // Aquí asignamos el id
                    }
                }
        } catch (e: Exception) {
            Log.d(TAG, "Excepcion en getAllRecetas")
            Log.d(TAG, e.toString())
            Log.d(TAG, e.stackTraceToString())
            emptyList<Receta>()
        }

    }

    override suspend fun getReceta(uuidReceta: String): Receta? {
        return try {
            val snapshot = db.collection("recetas")
                .document(uuidReceta)
                .get()
                .await()

            val receta = snapshot.toObject(Receta::class.java)
            // Cojo los nombres de los ingredientes
            // TODO: Podria hacerlo por batch... pero solo llega hasta 30, ojo
            receta?.apply {
                ingredientes.forEach { ingrediente ->
                    val ingredienteSnapshot = db.collection("ingredientes")
                        .document(ingrediente.ingredienteId).get().await()
                    ingrediente.nombre = ingredienteSnapshot.getString("nombre") ?: ""
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "Excepción en getReceta")
            Log.d(TAG, e.toString())
            null
        }
    }

    override suspend fun getRecetas(): List<Receta> {
        return try {

            val recetasSnapshot = db.collection("recetas").get().await()
            // Para cada receta
            recetasSnapshot.documents.mapNotNull { snapshot ->
                val receta = snapshot.toObject(Receta::class.java)?.apply {
                    uuidReceta = snapshot.id
                }

                // Cojo los nombres de los ingredientes
                // TODO: Podria hacerlo por batch... pero solo llega hasta 30, ojo
                receta?.apply {
                    ingredientes.forEach { ingrediente ->
                        val ingredienteSnapshot = db.collection("ingredientes")
                            .document(ingrediente.ingredienteId).get().await()
                        ingrediente.nombre = ingredienteSnapshot.getString("nombre") ?: ""
                    }
                }

            }

        } catch (e: Exception) {
            Log.d(TAG, "Excepcion en getRecetas")
            Log.d(TAG, e.toString())
            Log.d(TAG, e.stackTraceToString())
            emptyList()
        }
    }

}