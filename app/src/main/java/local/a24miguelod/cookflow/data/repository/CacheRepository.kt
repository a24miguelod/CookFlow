package local.a24miguelod.cookflow.data.repository

import kotlinx.coroutines.flow.Flow
import local.a24miguelod.cookflow.domain.model.Ingrediente

interface CacheRepository {
    fun insertIngrediente(ingrediente: Ingrediente)
    fun getIngredientes(): Flow<List<Ingrediente>>
    suspend fun setIngredienteDisponible(id: String, disponible: Boolean)

}