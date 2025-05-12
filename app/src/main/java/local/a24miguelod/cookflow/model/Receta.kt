package local.a24miguelod.cookflow.model

data class Receta(
    var uuidReceta: String = "uuid",
    var nombre: String = "",
    var descripcion: String = "",
    var ingredientes: List<IngredienteReceta> = emptyList(),
    var pasos: List<RecetaPaso> = emptyList(),
    var urlimagen: String = "",
)