package local.a24miguelod.cookflow.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import local.a24miguelod.cookflow.ui.DetalleRecetaScreen
import local.a24miguelod.cookflow.ui.DietarioScreen
import local.a24miguelod.cookflow.ui.ListaRecetasScreen


@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ListaRecetas
    ) {
        composable<ListaRecetas> {
            ListaRecetasScreen {
                idReceta -> navController.navigate(DetalleReceta(
                idReceta = 1
            ))}
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