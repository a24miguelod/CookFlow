package local.a24miguelod.cookflow.model

data class Receta(
    var idReceta: Int? = null,
    var nombre: String? = null,
    //var ingredientes: List<Ingrediente>?,
    //var pasos: List<RecetaPaso>?,
    var urlimagen: String? = null
)