package local.a24miguelod.cookflow.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object DestinationListaRecetasScreen

@Serializable
data class DestinationDetalleReceta(
    val recetaId: String
)