package local.a24miguelod.cookflow.presentation.screens.flow

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import local.a24miguelod.cookflow.domain.model.Receta

private const val TAG = "FlowScreen"

@Composable
fun FlowScreen(
    viewModel: FlowViewModel = viewModel(factory = FlowViewModel.Factory),
) {

    Log.d(TAG, "Antes de cargar viewmodel")
    val estado by viewModel.estado.collectAsState()
    Log.d(TAG, "Despues de cargar viewmodel")
    when (estado) {

        is FlowRecetasUIState.Error -> {
            val errorState = estado as FlowRecetasUIState.Error
            Log.d(TAG, errorState.message)
        }

        is FlowRecetasUIState.Loading -> {
            Log.d(TAG, "Loading")
        }

        is FlowRecetasUIState.Success -> {
            val sucessState = estado as FlowRecetasUIState.Success

            FlowScreenContent(
                receta = sucessState.receta,
                paso = sucessState.pasoActual,
                progreso = sucessState.progreso
            )
        }
    }
}

@Composable
fun FlowScreenContent(receta: Receta, paso: Int, progreso: Float) {

    Log.d(TAG, "RECETA=$receta")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = receta.nombre,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,

            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (paso < receta.pasos.size) {
            Text(
                text = "Paso ${paso + 1}: ${receta.pasos[paso].resumen}",
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = receta.pasos[paso].detallelargo,
                modifier = Modifier.padding(bottom = 16.dp)
            )

        } else {
            Text(
                text = "Que aproveche!",
                modifier = Modifier.padding(vertical = 24.dp)
            )
        }

        BarraDeProgreso(progreso)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {  },
                enabled = true
            ) {
                Text("Paso anterior")
            }

            Button(
                onClick = {  },
                enabled = true
            ) {
                Text("Paso siguiente")
            }
        }
    }
}



/*
@Composable
fun BarraDeProgreso(
    duracionEnMinutos: Float
) {
    val duracionTotalMillis = duracionEnMinutos * 60_000L

    // Flow que emite cada segundo el tiempo transcurrido
    val progresoFlow: Flow<Float> = remember(duracionEnMinutos) {
        flow {
            val startTime = System.currentTimeMillis()
            while (true) {
                val elapsed = System.currentTimeMillis() - startTime
                val progress = (elapsed.toFloat() / duracionTotalMillis).coerceIn(0f, 1f)
                emit(progress)

                if (progress >= 1f) break
                delay(1000L)
                Log.d(TAG, "tick tick")
            }
        }
    }

    //val progreso by progresoFlow.collectAsState(initial = 0f)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        LinearProgressIndicator(
            progress = progreso,
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .clip(RoundedCornerShape(6.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        val tiempoRestanteSegundos =
            ((duracionTotalMillis * (1 - progreso)) / 1000).toInt().coerceAtLeast(0)

        Text(
            text = "$tiempoRestanteSegundos segundos restantes",
        )
    }
}
*/

@Composable
fun BarraDeProgreso(
    progreso: Float
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        LinearProgressIndicator(
            progress = progreso,
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .clip(RoundedCornerShape(6.dp))
        )

    }
}