package local.a24miguelod.cookflow.data.repository

import local.a24miguelod.cookflow.data.local.dao.IngredienteDao
import local.a24miguelod.cookflow.data.local.mappers.toEntity
import local.a24miguelod.cookflow.domain.model.Ingrediente

class CacheRepositoryRoom (
    private val ingredienteDao: IngredienteDao
):CacheRepository
{
    override fun insertIngrediente(ingrediente: Ingrediente) {
        ingredienteDao.insert(ingrediente.toEntity())
    }
}