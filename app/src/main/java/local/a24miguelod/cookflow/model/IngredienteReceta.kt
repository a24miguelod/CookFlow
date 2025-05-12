package local.a24miguelod.cookflow.model

data class IngredienteReceta(
    var ingredienteId: String,
    var nombre: String,
    var cantidad: String
) {
    // Constructor sin argumentos necesario para la deserialización
    constructor() : this("", "", "")
}