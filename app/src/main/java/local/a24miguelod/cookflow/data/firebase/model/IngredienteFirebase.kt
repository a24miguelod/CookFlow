package local.a24miguelod.cookflow.data.firebase.model

data class IngredienteFirebase(
    var uuidIngredienteFirebase: String,
    val nombre: String
)
{
    // Constructor sin argumentos necesario para la deserialización
    constructor() : this( "", "")
}