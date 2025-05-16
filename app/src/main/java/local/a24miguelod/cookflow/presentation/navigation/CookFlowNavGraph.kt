package local.a24miguelod.cookflow.presentation.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import local.a24miguelod.cookflow.CookFlowApp
import local.a24miguelod.cookflow.common.dependencies.AppInyectorContainer
import local.a24miguelod.cookflow.common.dependencies.AppInyectorContainerImpl
import local.a24miguelod.cookflow.presentation.screens.despensa.DespensaScreen
import local.a24miguelod.cookflow.presentation.screens.detalle.DetalleRecetaScreen
import local.a24miguelod.cookflow.presentation.screens.flow.FlowScreen
import local.a24miguelod.cookflow.presentation.screens.lista.ListaRecetasScreen
import local.a24miguelod.cookflow.presentation.screens.lista.ListaRecetasViewModel
import local.a24miguelod.cookflow.presentation.screens.lista_compra.ListaCompraScreen
import local.a24miguelod.cookflow.presentation.viewModelFactory

private const val TAG = "CookFLowNavGraph"

@RequiresApi(Build.VERSION_CODES.O)
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
            val viewModel = viewModel<ListaRecetasViewModel> (
                    factory = viewModelFactory {
                        ListaRecetasViewModel(
                            CookFlowApp.appModule.repository
                        )
                    }
                )
            ListaRecetasScreen(
                onRecetaClick = { receta -> navActions.navigateToDetalleReceta(receta.id) }
            )
        }

        composable(CookFlowRoutes.RECETA_ROUTE) {
            Log.d(TAG, "en receta route. estoy aqui??")
            DetalleRecetaScreen(
                onFlowClick = { receta -> navActions.navigateToFlowReceta(receta.id) }
            )
        }

        composable(CookFlowRoutes.FLOW_ROUTE) {
            Log.d(TAG, "en flowreceta route")
            FlowScreen(
            )
        }

        composable(CookFlowRoutes.DESPENSA_ROUTE) {
            Log.d(TAG, "en flowreceta route")
            DespensaScreen()
        }

        composable(CookFlowRoutes.LISTA_COMPRA_ROUTE) {
            Log.d(TAG, "en flowreceta route")
            ListaCompraScreen()
        }
    }
}


