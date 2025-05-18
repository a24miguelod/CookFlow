package local.a24miguelod.cookflow.presentation.screens.flow

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import local.a24miguelod.cookflow.R

import local.a24miguelod.cookflow.domain.model.Receta
import local.a24miguelod.cookflow.presentation.screens.comun.CargandoGenerico
import local.a24miguelod.cookflow.presentation.screens.comun.ErrorGenerico
import local.a24miguelod.cookflow.presentation.screens.comun.formatDuracion
import local.a24miguelod.cookflow.presentation.screens.lista.ListaRecetasUIState

private const val TAG = "FlowScreen"

@Composable
fun FlowScreen(
    viewModel: FlowViewModel,
    onHomeClick: () -> Unit,
) {

    val estado by viewModel.estado.collectAsState()
    val cronometro by viewModel.cronometro.collectAsState()

    when (estado) {

        is FlowRecetasUIState.Error -> {
            val errorState = estado as FlowRecetasUIState.Error
            ErrorGenerico((estado as FlowRecetasUIState.Error).message)
            Log.d(TAG, errorState.message)
        }

        is FlowRecetasUIState.Loading -> {
            CargandoGenerico(stringResource(R.string.recuperando_datos))
        }

        is FlowRecetasUIState.Success -> {
            val sucessState = estado as FlowRecetasUIState.Success
            FlowScreenContent(
                receta = sucessState.receta,
                paso = sucessState.pasoActual,
                progreso = cronometro.progreso,
                onPreviousClick = { viewModel.mostrarPasoAnterior() },
                onNextClick = { viewModel.mostrarPasoSiguiente() },
                onHomeClick = onHomeClick
            )
        }
    }
}

@Composable
fun FlowScreenContent(
    receta: Receta,
    paso: Int,
    progreso: Float,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onHomeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = receta.nombre,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
            )

            if (paso < receta.pasos.size) {
                Column {
                    Text(
                        text = "Paso ${paso + 1}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = receta.pasos[paso].resumen,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text(
                        text = formatDuracion(receta.pasos[paso].duracion),
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )


                    Text(
                        text = receta.pasos[paso].detallelargo,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize().padding(60.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.size(70.dp))
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = "¡Que aproveche!",
                        modifier = Modifier.fillMaxSize().padding(20.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    OutlinedButton(
                        onClick = { onHomeClick() },
                        enabled = paso > 0
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Volver a Recetas")
                    }
                }
            }
        }

        // Barra de progreso y controles en la parte inferior
        if (paso < receta.pasos.size) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                LinearProgressIndicator(
                    progress = { progreso },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = onPreviousClick,
                        enabled = paso > 0
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Anterior")
                    }

                    Button(
                        onClick = onNextClick,
                        enabled = paso < receta.pasos.size
                    ) {
                        Text(if (paso < receta.pasos.size) "Siguiente" else "Recetas")
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                    }
                }
            }
        }
    }
}