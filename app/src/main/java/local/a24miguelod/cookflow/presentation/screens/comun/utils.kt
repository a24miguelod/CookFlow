package local.a24miguelod.cookflow.presentation.screens.comun

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun formatDuracion(minutos: Float): String {
    val totalSegundos = (minutos * 60).toInt()
    val hours = totalSegundos / 3600
    val minutes = (totalSegundos % 3600) / 60
    val seconds = totalSegundos % 60

    return when {
        hours > 0 -> {
            if (minutes > 0) "${hours}h ${minutes}min" else "${hours}h"
        }
        minutes > 0 -> {
            "${minutes} min"
        }
        else -> {
            "$seconds segundos"
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val fullRoute = navBackStackEntry?.destination?.route ?: return null
    // me quedo solo con el final
    return fullRoute.substringAfterLast(".")
}