package local.a24miguelod.cookflow.data.firebase.mappers

import local.a24miguelod.cookflow.common.Mapper
import local.a24miguelod.cookflow.data.firebase.model.IngredienteFirebase
import local.a24miguelod.cookflow.domain.model.Ingrediente

class IngredienteFirebaseToModel : Mapper<IngredienteFirebase?, Ingrediente?> {
    override fun mapFrom(from: IngredienteFirebase?): Ingrediente? {
        return from?.let {
            Ingrediente(nombre = it.nombre)
        }
    }
}