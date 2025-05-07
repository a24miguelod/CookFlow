package local.a24miguelod.cookflow.model.retrofit.gson

import local.a24miguelod.cookflow.model.Receta

class Recetas(val recetas: MutableList<Receta> = mutableListOf<Receta>()) {
    fun getAsList() : List<Receta> {
        return recetas
    }
}