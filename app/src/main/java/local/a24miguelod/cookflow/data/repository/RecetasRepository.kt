package local.a24miguelod.cookflow.data.repository

import local.a24miguelod.cookflow.model.Ingrediente
import local.a24miguelod.cookflow.model.Receta

interface RecetasRepository {
    suspend fun getRecetas(): List<Receta>
    suspend fun getReceta(uuidReceta: String): Receta?
    //suspend fun getIngredientes(): List<Ingrediente>
}