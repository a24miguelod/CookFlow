package local.a24miguelod.cookflow.data.repository

import kotlinx.coroutines.flow.Flow
import local.a24miguelod.cookflow.domain.model.Receta

interface RecetasRepository {
    //suspend fun getRecetas(): List<Receta>
    suspend fun getRecetasConFlow(): Flow<List<Receta>>

    // el ide es un mecanismo de acceso, no existe en el modelo del dominio
    suspend fun getReceta(id: String): Receta?
    //suspend fun getIngredientes(): List<Ingrediente>
}