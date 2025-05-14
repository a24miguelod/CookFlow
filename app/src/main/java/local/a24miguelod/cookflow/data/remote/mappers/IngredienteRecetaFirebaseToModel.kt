package local.a24miguelod.cookflow.data.remote.mappers

import local.a24miguelod.cookflow.common.Mapper
import local.a24miguelod.cookflow.data.remote.model.IngredienteRecetaFirebase
import local.a24miguelod.cookflow.data.remote.model.RecetaFirebase
import local.a24miguelod.cookflow.domain.model.Ingrediente
import local.a24miguelod.cookflow.domain.model.IngredienteReceta
import local.a24miguelod.cookflow.domain.model.Receta

class IngredienteRecetaFirebaseToModel : Mapper<IngredienteRecetaFirebase?, IngredienteReceta?> {
    override fun mapFrom(from: IngredienteRecetaFirebase?): IngredienteReceta? {
        return from?.let {
            IngredienteReceta(
                // TODO: Este mapper no sabe el nombre del ingrediente... hay que asignar
                // el nombre despues de la llamada al mapFrom
                ingrediente = Ingrediente("temporal"),
                cantidad = it.cantidad
            )
        }
    }


}