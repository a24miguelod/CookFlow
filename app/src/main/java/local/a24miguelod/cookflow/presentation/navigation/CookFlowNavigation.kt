package local.a24miguelod.cookflow.presentation.navigation

import android.util.Log
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import local.a24miguelod.cookflow.presentation.navigation.CockFlowDestinationsArgs.RECETA_ID
import local.a24miguelod.cookflow.presentation.navigation.CookFlowRoutes.DESPENSA_ROUTE
import local.a24miguelod.cookflow.presentation.navigation.CookFlowRoutes.LISTA_COMPRA_ROUTE
import local.a24miguelod.cookflow.presentation.navigation.CookFlowRoutes.RECETAS_ROUTE
import local.a24miguelod.cookflow.presentation.navigation.CookFlowScreens.DESPENSA_SCREEN
import local.a24miguelod.cookflow.presentation.navigation.CookFlowScreens.FLOW_SCREEN
import local.a24miguelod.cookflow.presentation.navigation.CookFlowScreens.LISTA_COMPRA_SCREEN
import local.a24miguelod.cookflow.presentation.navigation.CookFlowScreens.RECETAS_SCREEN
import local.a24miguelod.cookflow.presentation.navigation.CookFlowScreens.RECETA_SCREEN

private const val TAG="CookFlowNavigation"

/**
 * Screens usados en [ CookFlowDestinations]
 */
private object CookFlowScreens {
    const val RECETAS_SCREEN = "recetas"
    const val RECETA_SCREEN = "receta"
    const val FLOW_SCREEN = "flow"
    const val DESPENSA_SCREEN = "despensa"
    const val LISTA_COMPRA_SCREEN = "listacompora"
}

/**
 * Argumentso used in [CookFlowRoutes]
 */
object CockFlowDestinationsArgs {
    const val RECETA_ID = "recetaId"
}

/**
 * Destinos usados en la actividad principal
 */
object CookFlowRoutes {
    const val RECETAS_ROUTE = RECETAS_SCREEN
    const val RECETA_ROUTE = "$RECETA_SCREEN/{$RECETA_ID}"
    const val FLOW_ROUTE = "$FLOW_SCREEN/{$RECETA_ID}"
    const val DESPENSA_ROUTE = DESPENSA_SCREEN
    const val LISTA_COMPRA_ROUTE = LISTA_COMPRA_SCREEN
}

/**
 * Acciones de navegacion
 */
class CookFlowNavigationActions(private val navController: NavHostController) {

    fun navigateToRecetas() {
        navController.navigate(RECETAS_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }

    fun navigateToDetalleReceta(uuidReceta: String) {
        Log.d(TAG, "Navegar a receta '$RECETA_SCREEN/$uuidReceta' $uuidReceta")
        navController.navigate("$RECETA_SCREEN/${uuidReceta}")
    }

    fun navigateToFlowReceta(uuidReceta: String) {
        Log.d(TAG, "Comenzar flow a receta '$FLOW_SCREEN/$uuidReceta' $uuidReceta")
        navController.navigate("$FLOW_SCREEN/$uuidReceta")
    }

    fun navigateToDespensa() {
        navController.navigate(DESPENSA_ROUTE)
    }
    fun navigateToListaCompra() {
        navController.navigate(LISTA_COMPRA_ROUTE)
    }



}
