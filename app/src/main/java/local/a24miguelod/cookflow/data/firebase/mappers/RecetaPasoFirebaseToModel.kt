package local.a24miguelod.cookflow.data.firebase.mappers

import local.a24miguelod.cookflow.common.Mapper
import local.a24miguelod.cookflow.data.firebase.model.RecetaPasoFirebase
import local.a24miguelod.cookflow.domain.model.RecetaPaso


class RecetaPasoFirebaseToModel : Mapper<RecetaPasoFirebase?, RecetaPaso?> {
    override fun mapFrom(from: RecetaPasoFirebase?): RecetaPaso? {
        return from?.let {

            RecetaPaso(
                resumen = it.paso,
                detallelargo = it.comentarios,
                duracion = it.duracion
            )
        }
    }
}