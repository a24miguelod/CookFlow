package local.a24miguelod.cookflow.domain.model

data class Receta(
    // sin id!
    // https://semigrp.hashnode.dev/where-should-ids-be-numbered-in-a-clean-architecture
    // me retracto
    var id: String,
    var nombre: String = "",
    var descripcion: String = "",
    var ingredientes: MutableList<IngredienteReceta> = mutableListOf(),
    var pasos: MutableList<RecetaPaso> = mutableListOf(),
    var urlimagen: String = "",
)