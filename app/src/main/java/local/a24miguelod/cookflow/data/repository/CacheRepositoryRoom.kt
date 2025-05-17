package local.a24miguelod.cookflow.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import local.a24miguelod.cookflow.data.local.dao.IngredienteDao
import local.a24miguelod.cookflow.data.local.mappers.toDomain
import local.a24miguelod.cookflow.data.local.mappers.toEntity
import local.a24miguelod.cookflow.domain.model.Ingrediente

class CacheRepositoryRoom (
    private val ingredienteDao: IngredienteDao
):CacheRepository
{
    override fun insertIngrediente(ingrediente: Ingrediente) {
        ingredienteDao.insert(ingrediente.toEntity())
    }

    override fun getIngredientes(): Flow<List<Ingrediente>> {
        return ingredienteDao.getIngredientes().map {
                lista -> lista.map { it.toDomain() }}
    }

    override suspend fun setIngredienteDisponible(id: String, disponible: Boolean) {
        ingredienteDao.updateDisponibilidad(id, disponible )
    }


}