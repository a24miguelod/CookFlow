package local.a24miguelod.cookflow.data.remote.model

data class RecetaFirebase(
    var uuidReceta: String = "uuid",
    var nombre: String = "",
    var descripcion: String = "",
    var ingredientes: List<IngredienteRecetaFirebase>? = emptyList(),
    var pasos: List<RecetaPasoFirebase> = emptyList(),
    var favorito: Boolean = false,
    var urlimagen: String = "",
) {
    // Constructor sin argumentos necesario para la deserialización
    constructor() : this(
        "", "", "", emptyList(), emptyList(), false
    )
}