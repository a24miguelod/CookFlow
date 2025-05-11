package local.a24miguelod.cookflow

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import local.a24miguelod.cookflow.core.navigation.DetalleReceta
import local.a24miguelod.cookflow.core.navigation.Dietario
import local.a24miguelod.cookflow.core.navigation.ListaRecetas
import local.a24miguelod.cookflow.ui.screens.detalle.DetalleRecetaScreen
import local.a24miguelod.cookflow.ui.screens.dietario.DietarioScreen
import local.a24miguelod.cookflow.ui.screens.lista.ListaRecetasScreen


@Composable
fun CookFlowNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ListaRecetas
    ) {
        composable<ListaRecetas> {
            ListaRecetasScreen(
                navegarAlDetalle = { idReceta ->
                    navController.navigate(DetalleReceta(idReceta))
                },
                // TODO: viewModel = viewModel
            )
        }

        composable<DetalleReceta> { backStackEntry ->
            val idReceta = backStackEntry.toRoute<DetalleReceta>()
            DetalleRecetaScreen(idReceta.idReceta)

        }

        composable<Dietario> {
            DietarioScreen()
        }

    }
}
