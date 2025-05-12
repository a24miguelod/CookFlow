package local.a24miguelod.cookflow.data.repository

import kotlinx.coroutines.flow.Flow
import local.a24miguelod.cookflow.model.Ingrediente
import local.a24miguelod.cookflow.model.Receta

interface RecetasRepository {
    suspend fun getRecetas(): List<Receta>
    fun getRecetasConFlow(): Flow<List<Receta>>
    suspend fun getReceta(uuidReceta: String): Receta?
    //suspend fun getIngredientes(): List<Ingrediente>
}