package local.a24miguelod.cookflow.data.repository

import kotlinx.coroutines.flow.Flow
import local.a24miguelod.cookflow.domain.model.Ingrediente

interface CacheRepository {
    suspend fun insertIngrediente(ingrediente: Ingrediente)
    suspend fun getIngredienteCacheado(ingrediente: Ingrediente): Ingrediente
    fun getIngredientes(lista: List<Ingrediente>): Flow<List<Ingrediente>>
    fun getIngredientesPorIds(ids: List<String>): Flow<List<Ingrediente>>
    suspend fun getIngredienteById(id:String): Ingrediente?
    fun getAllIngredientes(): Flow<List<Ingrediente>>
    suspend fun setIngredienteDisponible(id: String, disponible: Boolean)
    suspend fun setIngredienteEnListaCompra(id: String, listaCompra: Boolean)

}