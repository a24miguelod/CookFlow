package local.a24miguelod.cookflow.presentation.navigation

import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import local.a24miguelod.cookflow.CookFlowApp
import local.a24miguelod.cookflow.R
import local.a24miguelod.cookflow.presentation.navigation.CookFlowDestinationsArgs.RECETA_ID
import local.a24miguelod.cookflow.presentation.screens.comun.currentRoute
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

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CookFlowNavGraph(
    navController: NavHostController,
) {

    val currentRoute = currentRoute(navController)
    Log.d(TAG, "ruta actual " + currentRoute.toString())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(DestinationListaRecetasScreen)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_chef),
                            contentDescription = stringResource(R.string.app_name),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
            )
            {
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Recetas") },
                    label = { Text(stringResource(R.string.recetas)) },
                    selected = currentRoute == DestinationListaRecetasScreen.route,
                    onClick = { navController.navigate(DestinationListaRecetasScreen) },
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.List, contentDescription = "Despensa") },
                    label = { Text(stringResource(R.string.despensa)) },
                    selected = currentRoute == DestinationDespensa.route,
                    onClick = { navController.navigate(DestinationDespensa) }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Compra") },
                    label = { Text(stringResource(R.string.compra)) },
                    selected = currentRoute == DestinationListaCompra.route,
                    onClick = { navController.navigate(DestinationListaCompra) }
                )
            }

            //}
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = DestinationListaRecetasScreen,
            modifier = Modifier.padding(innerPadding)
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
                )
            }
        }
    }

}


