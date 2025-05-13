package local.a24miguelod.cookflow

import android.util.Log
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import local.a24miguelod.cookflow.CockFlowDestinationsArgs.RECETA_ID
import local.a24miguelod.cookflow.CookFlowRoutes.RECETAS_ROUTE
import local.a24miguelod.cookflow.CookFlowScreens.RECETAS_SCREEN
import local.a24miguelod.cookflow.CookFlowScreens.RECETA_SCREEN

private const val TAG="CookFlowNavigation"

/**
 * Screens usados en [ CookFlowDestinations]
 */
private object CookFlowScreens {
    const val RECETAS_SCREEN = "recetas"
    const val RECETA_SCREEN = "receta"
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
}

/**
 * Acciones de navegacion
 */
class CookFlowNavigationActions(private val navController: NavHostController) {

    fun navigateToRecetas() {
        //  TODO: por probar, quiza desde el navigation drawer??
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
        Log.d(TAG, "Navegar a receta ${uuidReceta}")
        navController.navigate("$RECETA_SCREEN/$uuidReceta")
    }


}
