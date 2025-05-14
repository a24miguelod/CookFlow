package local.a24miguelod.cookflow.ui.screens.flow

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import local.a24miguelod.cookflow.domain.model.Ingrediente
import local.a24miguelod.cookflow.domain.model.IngredienteReceta
import local.a24miguelod.cookflow.domain.model.Receta
import local.a24miguelod.cookflow.domain.model.RecetaPaso
import local.a24miguelod.cookflow.ui.screens.detalle.DetalleRecetaUIState
import local.a24miguelod.cookflow.ui.screens.detalle.DetalleRecetaViewModel

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
            val errorState = estado as DetalleRecetaUIState.Error
            Log.d(TAG, errorState.message)
        }

        is FlowRecetasUIState.Loading -> {
            Log.d(TAG, "Loading")
        }

        is FlowRecetasUIState.Success -> {
            val sucessState = estado as DetalleRecetaUIState.Success
            val receta = sucessState.receta

            @Composable
            fun RecetaPasoAPaso(receta: Receta) {
                var pasoActual by remember { mutableStateOf(0) }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = receta.nombre,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    if (pasoActual < receta.pasos.size) {
                        Text(
                            text = "Paso ${pasoActual + 1}: ${receta.pasos[pasoActual]}",
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    } else {
                        Text(
                            text = "Que aproveche!",
                            modifier = Modifier.padding(vertical = 24.dp)
                        )
                    }

                    // Barra de progreso basada en la duración
                    val progreso = (pasoActual + 1).toFloat() / (receta.pasos.size + 1)
                    LinearProgressIndicator(
                        progress = { progreso },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .padding(vertical = 16.dp),
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { if (pasoActual > 0) pasoActual-- },
                            enabled = pasoActual > 0
                        ) {
                            Text("Paso anterior")
                        }

                        Button(
                            onClick = { if (pasoActual <= receta.pasos.size) pasoActual++ },
                            enabled = pasoActual < receta.pasos.size
                        ) {
                            Text("Paso siguiente")
                        }
                    }
                }
            }

        }
    }
}

@Preview
@Composable
fun FlowScreenPreview() {
    val receta = Receta(
        nombre = "Pizza Casera Margarita",
        descripcion = "Una receta sencilla para preparar una pizza casera deliciosa con tomate, mozzarella y albahaca.",
        ingredientes = mutableListOf(
            IngredienteReceta(Ingrediente("Sal"), "un puñado"),
            IngredienteReceta(Ingrediente("Sal"), "un puñado"),
            IngredienteReceta(Ingrediente("Sal"), "un puñado")
        ),
        id ="TODO",
        pasos = mutableListOf(
            RecetaPaso("Cortar las cebollas", "Hacer esto y lo otro", 1f),
        RecetaPaso("Cortar las cebollas", "Hacer esto y lo otro", 1f),
        RecetaPaso("Cortar las cebollas", "Hacer esto y lo otro", 1f),
        RecetaPaso("Cortar las cebollas", "Hacer esto y lo otro", 1f),
        RecetaPaso("Cortar las cebollas", "Hacer esto y lo otro", 1f)
        ),
        urlimagen = "no importa"
    )

    var pasoActual = 0;

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = receta.nombre,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (pasoActual < receta.pasos.size) {
            Text(
                text = "Paso ${pasoActual + 1}: ${receta.pasos[pasoActual]}",
                modifier = Modifier.padding(bottom = 16.dp)
            )
        } else {
            Text(
                text = "Que aproveche!",
                modifier = Modifier.padding(vertical = 24.dp)
            )
        }

        // Barra de progreso basada en la duración
        val progreso = (pasoActual + 1).toFloat() / (receta.pasos.size + 1)
        LinearProgressIndicator(
            progress = { progreso },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .padding(vertical = 16.dp),
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { if (pasoActual > 0) pasoActual-- },
                enabled = pasoActual > 0
            ) {
                Text("Paso anterior")
            }

            Button(
                onClick = { if (pasoActual <= receta.pasos.size) pasoActual++ },
                enabled = pasoActual < receta.pasos.size
            ) {
                Text("Paso siguiente")
            }
        }
    }
}