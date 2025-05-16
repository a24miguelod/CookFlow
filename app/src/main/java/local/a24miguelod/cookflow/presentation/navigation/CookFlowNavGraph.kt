package local.a24miguelod.cookflow.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import local.a24miguelod.cookflow.presentation.screens.detalle.DetalleRecetaScreen
import local.a24miguelod.cookflow.presentation.screens.flow.FlowScreen
import local.a24miguelod.cookflow.presentation.screens.lista.ListaRecetasScreen

private const val TAG = "CookFLowNavGraph"

@Composable
fun CookFlowNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = CookFlowRoutes.RECETAS_ROUTE,
    navActions: CookFlowNavigationActions = remember(navController) {
        CookFlowNavigationActions(navController)
    }
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(CookFlowRoutes.RECETAS_ROUTE) {
            ListaRecetasScreen(
                onRecetaClick = { receta -> navActions.navigateToDetalleReceta(receta.id)}
            )
        }

        composable(CookFlowRoutes.RECETA_ROUTE) {
            Log.d(TAG, "en receta route. estoy aqui??")
            DetalleRecetaScreen(
                onFlowClick = { receta -> navActions.navigateToFlowReceta(receta.id)}
            )
        }

        composable(CookFlowRoutes.FLOW_ROUTE) {
            Log.d(TAG, "en flowreceta route")
            FlowScreen(
            )
        }
    }
}


