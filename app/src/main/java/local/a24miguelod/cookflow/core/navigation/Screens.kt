package local.a24miguelod.cookflow.core.navigation

import kotlinx.serialization.Serializable

@Serializable
object ListaRecetas

@Serializable
data class DetalleReceta(val idReceta: Int)

@Serializable
object Dietario