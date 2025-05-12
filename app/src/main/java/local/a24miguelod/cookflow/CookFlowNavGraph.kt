package local.a24miguelod.cookflow

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import local.a24miguelod.cookflow.ui.screens.detalle.DetalleRecetaScreen
import local.a24miguelod.cookflow.ui.screens.lista.ListaRecetasScreen

//
//@Composable
//fun CookFlowNavGraph2() {
//    val navController = rememberNavController()
//
//    NavHost(
//        navController = navController,
//        startDestination = ListaRecetas
//    ) {
//        composable<ListaRecetas> {
//            ListaRecetasScreen(
//                navegarAlDetalle = { idReceta ->
//                    navController.navigate(DetalleReceta(idReceta))
//                },
//                // TODO: viewModel = viewModel
//            )
//        }
//
//        composable<DetalleReceta> { backStackEntry ->
//            val idReceta = backStackEntry.toRoute<DetalleReceta>()
//            DetalleRecetaScreen(idReceta.idReceta)
//
//        }
//
//        composable<Dietario> {
//            DietarioScreen()
//        }
//
//    }
//}

private const val TAG = "CookFLowNavGraph"
@Composable
fun CookFlowNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    // TODO: drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    startDestination: String = CookFlowRoutes.RECETAS_ROUTE,
    navActions: CookFlowNavigationActions = remember(navController) {
        CookFlowNavigationActions(navController)
    }
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(CookFlowRoutes.RECETAS_ROUTE) {
            Log.d(TAG, "antes de ListaRecetasScreen")
            ListaRecetasScreen(
                onRecetaClick = { receta -> navActions.navigateToDetalleReceta(receta.uuidReceta)}
            )
        }

        composable(CookFlowRoutes.RECETA_ROUTE) {
            Log.d(TAG, "en receta route")
            DetalleRecetaScreen()
        }

    }
}


