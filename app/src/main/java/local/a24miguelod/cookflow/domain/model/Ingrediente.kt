package local.a24miguelod.cookflow.domain.model

import java.util.UUID

data class Ingrediente (
  val ingredienteId: String = UUID.randomUUID().toString(),
  val nombre:String,
)
