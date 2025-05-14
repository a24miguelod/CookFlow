package local.a24miguelod.cookflow.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import local.a24miguelod.cookflow.data.remote.mappers.RecetaFirebaseToModel
import local.a24miguelod.cookflow.data.remote.model.RecetaFirebase
import local.a24miguelod.cookflow.domain.model.Ingrediente
import local.a24miguelod.cookflow.domain.model.IngredienteReceta
import local.a24miguelod.cookflow.domain.model.Receta

private const val TAG = "RecetasRepositoryFirebase"

class RecetasRepositoryFirebase : RecetasRepository {

    private var db: FirebaseFirestore = Firebase.firestore
    private var ingredientesMapCache: MutableMap<String, Ingrediente> = mutableMapOf()

    /*
        suspend fun getRecetasSinNombreIngredientes(): List<Receta> {

            return try {
                db.collection("recetas")
                    .get()
                    .await()
                    .documents
                    .mapNotNull { snapshot ->
                        val receta = snapshot.toObject(RecetaFirebase::class.java)
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
                // TODO: Podria hacerlo por batch y en corutina
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
    */

    /*
    override suspend fun getRecetas(): List<Receta> {
        return try {

            val recetasSnapshot = db.collection("recetas").get().await()

            recetasSnapshot.documents.mapNotNull { snapshot ->
                val recetaFirebase = snapshot.toObject(RecetaFirebase::class.java)?.apply {
                    uuidReceta = snapshot.id
                }

                /*
                Recuperamos la lista de ingredientes (en string) para poder consultar
                todos juntos a Firebase
                 */
                val stringsIdIngredientes = recetaFirebase?.ingredientes?.map {
                    it.uuidIngrediente
                }

                /*
                Para devolver la receta necesito un array de IngredienteReceta. Obtengo
                los nombres con el mapeador de IngredienteFirebaseToModel y voy creando
                el array de IngredienteReceta (del dominio)
                 */
                val ingredientesRecetaFirebase = getIngredienteRecetasPorIds(recetaFirebase?.ingredientes)
                val ingredientesReceta: List<IngredienteReceta> = ingredientesRecetaFirebase.mapNotNull {
                    IngredienteRecetaFirebaseToModel().mapFrom(it)
                }

                val receta = RecetaFirebaseToModel().mapFrom(recetaFirebase)
                receta!!.ingredientes = ingredientesReceta

            }

             emptyList()

        } catch (e: Exception) {
            Log.d(TAG, "Excepcion en getRecetas")
            Log.d(TAG, e.toString())
            Log.d(TAG, e.stackTraceToString())
            emptyList()
        }
    }

      override suspend fun getReceta(id: String): Receta? {
        TODO("Not yet implemented")
    }
*/
    /*
    override fun getRecetasConFlow(): Flow<List<Receta>> = flow {
        val resultadoParcial = mutableListOf<Receta>()

        val documentos = db.collection("recetas").get().await().documents
        for (doc in documentos) {
            val receta = doc.toObject(Receta::class.java)?.apply {
                uuidReceta = doc.id
            }
            delay(1000)
            if (receta != null) {
                resultadoParcial.add(receta)
                emit(resultadoParcial.toList()) // Emitimos una copia
            }
        }
    }
     */

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

                    resultadoParcial.add(receta)
                    emit(resultadoParcial.toList()) // Emitimos una copia
                }
            }


        }
    }

    override suspend fun getReceta(id: String): Receta? {
        TODO("Not yet implemented")
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