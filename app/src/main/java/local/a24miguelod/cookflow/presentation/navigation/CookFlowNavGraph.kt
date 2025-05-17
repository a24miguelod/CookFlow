package local.a24miguelod.cookflow.presentation.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import local.a24miguelod.cookflow.CookFlowApp
import local.a24miguelod.cookflow.presentation.screens.despensa.DespensaScreen
import local.a24miguelod.cookflow.presentation.screens.despensa.DespensaViewModel
import local.a24miguelod.cookflow.presentation.screens.detalle.DetalleRecetaScreen
import local.a24miguelod.cookflow.presentation.screens.detalle.DetalleRecetaViewModel
import local.a24miguelod.cookflow.presentation.screens.flow.FlowScreen
import local.a24miguelod.cookflow.presentation.screens.flow.FlowViewModel
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
    startDestination: DestinationListaRecetasScreen = DestinationListaRecetasScreen,
    navActions: CookFlowNavigationActions = remember(navController) {
        CookFlowNavigationActions(navController)
    }
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable<DestinationListaRecetasScreen>() {
            val viewModel = viewModel<ListaRecetasViewModel>(
                factory = viewModelFactory {
                    ListaRecetasViewModel(
                        CookFlowApp.contenedor.recetasRepository
                    )
                }
            )
            ListaRecetasScreen(
                viewModel,
                onRecetaClick = { receta ->
                    navController.navigate(
                        DestinationDetalleReceta(
                            recetaId = receta.id
                        )
                    )
                },
                onDespensaClick = { navController.navigate(DestinationDespensa) },
                onListaCompraClick = { navController.navigate(DestinationDespensa) },
            )
        }

        composable<DestinationDetalleReceta>() { navBackStackEntry ->
            val args = navBackStackEntry.toRoute<DestinationDetalleReceta>()
            val savedStateHandle = SavedStateHandle(mapOf("recetaId" to args.recetaId))
            val viewModel = viewModel<DetalleRecetaViewModel>(
                factory = viewModelFactory {
                    DetalleRecetaViewModel(
                        CookFlowApp.contenedor.recetasRepository,
                        CookFlowApp.contenedor.cacheRepository,
                        savedStateHandle = savedStateHandle
                    )
                }
            )

            DetalleRecetaScreen(
                viewModel,
                onFlowClick = { receta ->
                    navController.navigate(DestinationFlowReceta(receta.id))
                },
                onAnadirAListaCompra = { ingredienteReceta ->
                    viewModel.anadirAListaCompra(ingredienteReceta.ingrediente)
                },
                onToggleDespensa = { ingredienteReceta ->
                    viewModel.toggleDespensa(ingredienteReceta.ingrediente)
                }
            )
        }

        composable<DestinationFlowReceta>() { navBackStackEntry ->
            val args = navBackStackEntry.toRoute<DestinationFlowReceta>()
            val savedStateHandle = SavedStateHandle(mapOf("recetaId" to args.recetaId))
            val viewModel = viewModel<FlowViewModel>(
                factory = viewModelFactory {
                    FlowViewModel(
                        CookFlowApp.contenedor.recetasRepository,
                        savedStateHandle = savedStateHandle
                    )
                }
            )
            FlowScreen(
                viewModel
            )
        }

        composable<DestinationDespensa>() {
            val viewModel = viewModel<DespensaViewModel>(
                factory = viewModelFactory {
                    DespensaViewModel(
                        CookFlowApp.contenedor.cacheRepository,
                    )
                }
            )
            DespensaScreen(
                viewModel,
                onToggleDisponible = {},
                onAnadirAlCarrito = { }
            )
        }

        composable<DestinationListaCompra>() {
            val viewModel = viewModel<DespensaViewModel>(
                factory = viewModelFactory {
                    DespensaViewModel(
                        CookFlowApp.contenedor.cacheRepository,
                    )
                }
            )
            DespensaScreen(
                viewModel,
                onToggleDisponible = {},
                onAnadirAlCarrito = { }
            )
        }
    }
}


