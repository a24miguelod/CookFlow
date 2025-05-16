package local.a24miguelod.cookflow.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import local.a24miguelod.cookflow.data.firebase.mappers.RecetaFirebaseToModel
import local.a24miguelod.cookflow.data.firebase.mappers.RecetaPasoFirebaseToModel
import local.a24miguelod.cookflow.data.firebase.model.RecetaFirebase
import local.a24miguelod.cookflow.domain.model.Ingrediente
import local.a24miguelod.cookflow.domain.model.IngredienteReceta
import local.a24miguelod.cookflow.domain.model.Receta

private const val TAG = "RecetasRepositoryFirebase"

class RecetasRepositoryFirebase : RecetasRepository {

    private var db: FirebaseFirestore = Firebase.firestore
    private var ingredientesMapCache: MutableMap<String, Ingrediente> = mutableMapOf()

    override suspend fun getRecetasConFlow(): Flow<List<Receta>> = flow {
        val resultadoParcial = mutableListOf<Receta>()
        Log.d(TAG, "Empiezo a leer el flow")
        val documentos = db.collection("recetas").get().await().documents
        for (doc in documentos) {
            Log.d(TAG, "Leo documento")
            val recetaFirebase = doc.toObject(RecetaFirebase::class.java)?.apply {
                uuidReceta = doc.id
            }

            /*
            Recuperamos la lista de ingredientes (en string) para poder consultar
            todos juntos a Firebase. Ahora mismo se cachea en un Map.
            TODO: Poner el cacheo en otra clase
             */
            val stringsIdIngredientes = recetaFirebase?.ingredientes?.map {
                it.ingredienteId
            }

            cacheaIngredientesPorIds(stringsIdIngredientes)

            // Mapeo la recetaFirebase al objeto del dominio y añado IngredienteReceta
            val receta = RecetaFirebaseToModel().mapFrom(recetaFirebase)

            recetaFirebase?.ingredientes?.forEach {
                val ingrediente = ingredientesMapCache[it.ingredienteId]
                if (receta != null) {
                    if (ingrediente != null) {
                        receta.ingredientes.add(
                            IngredienteReceta(
                                ingrediente = ingrediente,
                                cantidad = it.cantidad
                            )
                        )
                    }
                }
            }
            recetaFirebase?.pasos?.forEach {
                if (receta != null) {
                    val paso = RecetaPasoFirebaseToModel().mapFrom(it)
                    if (paso != null) {
                        receta.pasos.add(paso)
                    }
                }
            }
            if (receta != null) {
                resultadoParcial.add(receta)
                emit(resultadoParcial.toList()) // Emitimos una copia
            }
        }
    }

    override suspend fun getReceta(id: String): Receta? {
        Log.d(TAG, "getReceta $id")
        val snapshot = db.collection("recetas")
            .document(id)  //
            .get()
            .await()
        val recetaFirebase = snapshot.toObject(RecetaFirebase::class.java)?.apply {
            uuidReceta = snapshot.id
        }

        val receta = RecetaFirebaseToModel().mapFrom(recetaFirebase)

        //TODO:Refactorizar, se repite en getRecetas
        recetaFirebase?.ingredientes?.forEach {
            val ingrediente = ingredientesMapCache[it.ingredienteId]
            if (receta != null) {
                if (ingrediente != null) {
                    receta.ingredientes.add(
                        IngredienteReceta(
                            ingrediente = ingrediente,
                            cantidad = it.cantidad
                        )
                    )
                }
            }
        }
        recetaFirebase?.pasos?.forEach {
            if (receta != null) {
                val paso = RecetaPasoFirebaseToModel().mapFrom(it)
                Log.d(TAG, "Deberia anadir el paso ${it.toString()}")
                Log.d(TAG, "Deberia anadir el paso ${paso.toString()}")
                if (paso != null) {
                    receta.pasos.add(paso)
                }
            }
        }
        return receta;
    }


    private suspend fun cacheaIngredientesPorIds(ids: List<String>?): Unit {

        val idsAConsultar = ids?.filterNot { ingredientesMapCache.containsKey(it) }
        if (idsAConsultar.isNullOrEmpty()) return

        Log.d(TAG, "Consulto la lista de ids:$ids")
        Log.d(TAG, "Despues de filtarar: :$idsAConsultar")

        // TODO: Hacer en batch
        for (id in idsAConsultar) {
            Log.d(TAG, "Consulto el ingrediente con id '$id'")
            val snapshot = db.collection("ingredientes")
                .document(id)  //
                .get()
                .await()

            snapshot.getString("nombre")?.let {
                Log.d(TAG, "Encuentro $it")
                ingredientesMapCache[id] = Ingrediente(it)
            }

        }
    }

}