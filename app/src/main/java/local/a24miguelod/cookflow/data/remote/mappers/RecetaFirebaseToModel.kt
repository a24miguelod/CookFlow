package local.a24miguelod.cookflow.data.remote.mappers

import local.a24miguelod.cookflow.common.Mapper
import local.a24miguelod.cookflow.data.remote.model.RecetaFirebase
import local.a24miguelod.cookflow.domain.model.Receta

class RecetaFirebaseToModel : Mapper<RecetaFirebase?, Receta?> {
    override fun mapFrom(from: RecetaFirebase?): Receta? {
        return from?.let {

            Receta(
                id = it.uuidReceta,
                nombre = it.nombre,
                descripcion = it.descripcion,
                ingredientes = mutableListOf(),
                pasos = mutableListOf(),
                urlimagen = it.urlimagen
            )
        }
    }


}