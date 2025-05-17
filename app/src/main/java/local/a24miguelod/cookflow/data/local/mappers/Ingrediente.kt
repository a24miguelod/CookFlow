package local.a24miguelod.cookflow.data.local.mappers

import local.a24miguelod.cookflow.data.local.entities.IngredienteEntity
import local.a24miguelod.cookflow.domain.model.Ingrediente

fun IngredienteEntity.toDomain(): Ingrediente =
    Ingrediente(ingredienteId = this.ingredienteId, nombre = this.nombre)

fun Ingrediente.toEntity(): IngredienteEntity =
    IngredienteEntity(ingredienteId = this.ingredienteId, nombre = this.nombre)