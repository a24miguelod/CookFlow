package local.a24miguelod.cookflow.data.github.model

import local.a24miguelod.cookflow.data.firebase.model.IngredienteRecetaFirebase
import local.a24miguelod.cookflow.data.firebase.model.RecetaPasoFirebase
import local.a24miguelod.cookflow.domain.model.Ingrediente
import local.a24miguelod.cookflow.domain.model.RecetaPaso

data class RecetaGithub(
    var uuidReceta: String = "uuid",
    var url: String,
    var nombre: String = "",
    var descripcion: String = "",
    var ingredientes: List<Ingrediente>? = emptyList(),
    var pasos: List<RecetaPaso> = emptyList(),
    var favorito: Boolean = false,
    var urlimagen: String = "",
)