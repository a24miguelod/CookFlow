package local.a24miguelod.cookflow.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import local.a24miguelod.cookflow.CookFlowApp
import local.a24miguelod.cookflow.presentation.navigation.CookFlowDestinationsArgs.RECETA_ID
import local.a24miguelod.cookflow.presentation.screens.despensa.DespensaScreen
import local.a24miguelod.cookflow.presentation.screens.despensa.DespensaViewModel
import local.a24miguelod.cookflow.presentation.screens.despensa.ListaCompraScreen
import local.a24miguelod.cookflow.presentation.screens.detalle.DetalleRecetaScreen
import local.a24miguelod.cookflow.presentation.screens.detalle.DetalleRecetaViewModel
import local.a24miguelod.cookflow.presentation.screens.flow.FlowScreen
import local.a24miguelod.cookflow.presentation.screens.flow.FlowViewModel
import local.a24miguelod.cookflow.presentation.screens.lista.ListaRecetasScreen
import local.a24miguelod.cookflow.presentation.screens.lista.ListaRecetasViewModel
import local.a24miguelod.cookflow.presentation.screens.lista_compra.ListaCompraViewModel

import local.a24miguelod.cookflow.presentation.viewModelFactory

private const val TAG = "CookFLowNavGraph"

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CookFlowNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: DestinationListaRecetasScreen = DestinationListaRecetasScreen,
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable<DestinationListaRecetasScreen> {
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
                onListaCompraClick = { navController.navigate(DestinationListaCompra) },
                onHomeClick = { navController.navigate(DestinationListaRecetasScreen) },
            )
        }

        composable<DestinationDetalleReceta> { navBackStackEntry ->
            val args = navBackStackEntry.toRoute<DestinationDetalleReceta>()
            val savedStateHandle = SavedStateHandle(mapOf(RECETA_ID to args.recetaId))
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
                },
                onDespensaClick = { navController.navigate(DestinationDespensa) },
                onListaCompraClick = { navController.navigate(DestinationListaCompra) },
                onHomeClick = { navController.navigate(DestinationListaRecetasScreen) },
            )
        }

        composable<DestinationFlowReceta> { navBackStackEntry ->
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
                viewModel,
                onHomeClick = {
                    // Quiero que en el ultimo paso (boton de volver a la lista de recetas)
                    // la app elimine esta pantalla del stack, pero que desde la lista
                    // de recetas, si pulso "back", vuelva a la receta que habia seleccionado
                    // antes
                    navController.navigate(DestinationListaRecetasScreen) {
                        popUpTo(DestinationFlowReceta(args.recetaId)) {
                            inclusive = true // elimina FlowScreen del stack
                        }
                        launchSingleTop = true // evita duplicados si ya estás en lista
                    }

                }
            )
        }

        composable<DestinationDespensa> {
            val viewModel = viewModel<DespensaViewModel>(
                factory = viewModelFactory {
                    DespensaViewModel(
                        CookFlowApp.contenedor.cacheRepository,
                    )
                }
            )
            DespensaScreen(
                viewModel,
                onToggleDespensa = { ingrediente ->
                    viewModel.toggleDespensa(ingrediente)
                },
                onAnadirAlCarrito = { ingrediente ->
                    viewModel.anadirAListaCompra(ingrediente)
                },
                onHomeClick = { navController.navigate(DestinationListaRecetasScreen) },
                onDespensaClick = { navController.navigate(DestinationDespensa) },
                onListaCompraClick = { navController.navigate(DestinationListaCompra) }
            )
        }

        composable<DestinationListaCompra> {
            val viewModel = viewModel<ListaCompraViewModel>(
                factory = viewModelFactory {
                    ListaCompraViewModel(
                        CookFlowApp.contenedor.cacheRepository,
                    )
                }
            )
            ListaCompraScreen(
                viewModel,
                onEliminarDeListaDeLaCompra = { ingrediente ->
                    viewModel.eliminarDeListaDeLaCompra(ingrediente)
                },
                onHomeClick = { navController.navigate(DestinationListaRecetasScreen) },
                onDespensaClick = { navController.navigate(DestinationDespensa) },
                onListaCompraClick = { navController.navigate(DestinationListaCompra) }
            )
        }
    }
}


