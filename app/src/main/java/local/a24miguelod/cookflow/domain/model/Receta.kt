package local.a24miguelod.cookflow.domain.model

import local.a24miguelod.cookflow.model.Paso

data class Receta(
    // sin id!
    // https://semigrp.hashnode.dev/where-should-ids-be-numbered-in-a-clean-architecture
    var nombre: String = "",
    var descripcion: String = "",
    var ingredientes: MutableList<IngredienteReceta> = mutableListOf(),
    var pasos: MutableList<Paso> = mutableListOf(),
    var urlimagen: String = "",
)