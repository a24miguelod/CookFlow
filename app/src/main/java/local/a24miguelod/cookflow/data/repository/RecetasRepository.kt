package local.a24miguelod.cookflow.data.repository

import kotlinx.coroutines.flow.Flow
import local.a24miguelod.cookflow.domain.model.Receta

interface RecetasRepository {
    suspend fun getRecetasConFlow(): Flow<List<Receta>>
    suspend fun getReceta(id: String): Receta?
}