package local.a24miguelod.cookflow.data.repository

import local.a24miguelod.cookflow.domain.model.Ingrediente

interface CacheRepository {
    fun insertIngrediente(ingrediente: Ingrediente)

}