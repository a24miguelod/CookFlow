package local.a24miguelod.cookflow.presentation.screens.comun

import androidx.compose.runtime.Composable

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