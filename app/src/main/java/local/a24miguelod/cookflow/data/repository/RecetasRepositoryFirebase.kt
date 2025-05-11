package local.a24miguelod.cookflow.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import local.a24miguelod.cookflow.model.Ingrediente
import local.a24miguelod.cookflow.model.Receta

private const val TAG = "RecetasRepositoryFirebase"

class RecetasRepositoryFirebase : RecetasRepository {

    private var db: FirebaseFirestore = Firebase.firestore

    override suspend fun getRecetas(): List<Receta> {

        return try {
            db.collection("recetas")
                .get()
                .await()
                .documents
                .mapNotNull { snapshot ->
                    snapshot.toObject(Receta::class.java)
                }
        } catch (e: Exception) {
            Log.d(TAG, "Excepcion en getAllRecetas")
            Log.d(TAG, e.toString())
            Log.d(TAG, e.stackTraceToString())
            emptyList<Receta>()
        }

    }


    override suspend fun getIngredientes(): List<Ingrediente> {
        TODO("Not yet implemented")
    }
}