package local.a24miguelod.cookflow.data.remote.model

data class IngredienteRecetaFirebase(
    var uuidIngredienteRecetaFirebase: String,
    var ingredienteId: String,
    val cantidad: String
)
{
    // Constructor sin argumentos necesario para la deserialización
    constructor() : this("","", "")
}