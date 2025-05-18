package local.a24miguelod.cookflow.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object DestinationListaRecetasScreen {
    const val route = "DestinationListaRecetasScreen"
}

@Serializable
data class DestinationDetalleReceta(
    val recetaId: String
) {
    companion object {
        const val baseRoute = "DestinationDetalleReceta"
        fun createRoute(recetaId: String) = "$baseRoute/$recetaId"
    }
    fun route() = createRoute(recetaId)
}

@Serializable
data class DestinationFlowReceta(
    val recetaId: String
) {
    companion object {
        const val baseRoute = "DestinationFlowReceta"
        fun createRoute(recetaId: String) = "$baseRoute/$recetaId"
    }
    fun route() = createRoute(recetaId)
}

@Serializable
object DestinationDespensa {
    const val route = "DestinationDespensa"
}

@Serializable
object DestinationListaCompra {
    const val route = "DestinationListaCompra"
}


