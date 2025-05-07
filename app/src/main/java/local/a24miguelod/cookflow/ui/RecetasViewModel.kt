package local.a24miguelod.cookflow.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import local.a24miguelod.cookflow.model.Receta
import local.a24miguelod.cookflow.model.retrofit.gson.Recetas
import local.a24miguelod.cookflow.utils.cargarIngredientes

private const val TAG = "RecetasViewModel"

class RecetasViewModel: ViewModel() {
    private val _recetas = MutableStateFlow<List<Receta>>(emptyList())
    val recetas: StateFlow<List<Receta>> = _recetas

    private var db:FirebaseFirestore = Firebase.firestore


    init {
        getRecetas()
    }

    private fun getRecetas() {
        viewModelScope.launch {
            val result: List<Receta> = withContext(Dispatchers.IO) {
                getAllRecetas()
            }
            _recetas.value = result
        }
    }

    suspend fun getAllRecetas():List<Receta> {
        return try {
            db.collection("recetas")
                .get()
                .await()
                .documents
                .mapNotNull {
                        snapshot -> snapshot.toObject(Receta::class.java)
                }
        } catch (e:Exception) {
            Log.d(TAG, "Excepcion en getAllRecetas")
            Log.d(TAG, e.toString())
            Log.d(TAG, e.stackTraceToString())
            emptyList<Receta>()
        }

    }

}