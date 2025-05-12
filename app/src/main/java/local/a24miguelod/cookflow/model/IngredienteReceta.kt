package local.a24miguelod.cookflow.model

data class IngredienteReceta(
    val nombre: String,
    val cantidad: String
) {
    // Constructor sin argumentos necesario para la deserialización
    constructor() : this("", "")
}

