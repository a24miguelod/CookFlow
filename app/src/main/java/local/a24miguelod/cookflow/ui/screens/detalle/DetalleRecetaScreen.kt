package local.a24miguelod.cookflow.ui.screens.detalle

import android.graphics.Paint.Align
import android.util.Log
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import local.a24miguelod.cookflow.CockFlowDestinationsArgs
import local.a24miguelod.cookflow.model.Ingrediente
import local.a24miguelod.cookflow.model.IngredienteReceta
import local.a24miguelod.cookflow.model.Receta
import local.a24miguelod.cookflow.model.RecetaPaso
import local.a24miguelod.cookflow.ui.screens.lista.ListaRecetasContent
import local.a24miguelod.cookflow.ui.screens.lista.ListaRecetasUIState
import local.a24miguelod.cookflow.ui.screens.lista.ListaRecetasViewModel

private const val TAG = "DetalleRecetaScreen"

@Composable
fun DetalleRecetaScreen(
    viewModel: DetalleRecetaViewModel = viewModel(factory = DetalleRecetaViewModel.Factory),
) {

    Log.d(TAG, "Antes de cargar viewmodel")
    val estado by viewModel.estado.collectAsState()
    Log.d(TAG, "Despues de cargar viewmodel")
    when (estado) {

        is DetalleRecetaUIState.Error -> {
            val errorState = estado as DetalleRecetaUIState.Error
            Log.d(TAG, errorState.message)
        }

        is DetalleRecetaUIState.Loading -> {
            Log.d(TAG, "Loading")
        }

        is DetalleRecetaUIState.Success -> {
            val sucessState = estado as DetalleRecetaUIState.Success
            val receta = sucessState.receta
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                AsyncImage(
                    model = receta.urlimagen,
                    contentDescription = receta.descripcion,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .fillMaxWidth()
                        .height(300.dp)
                )
                Spacer(modifier = Modifier.weight(6f))

                ListaIngredientes(receta.ingredientes)
                ListaPasos(receta.pasos)

                Button(onClick = {}) {
                    Text(text = "Ir al lista")
                }

            }
        }
    }
}

/*
@Preview
@Composable
fun ListaIngredientesPreview(

) {
    val ingredientes = listOf(
        IngredienteReceta("ajo", "2 cabezas"),
        IngredienteReceta("sal", "un puñado")
    )
    LazyColumn(
        modifier = Modifier.padding(5.dp)
    ) {
        items(ingredientes) { ingrediente ->
            Row(
                modifier = Modifier.padding(6.dp)
                    .fillParentMaxWidth()
            )
            {
                Text(ingrediente.nombre)
                Text(ingrediente.cantidad)
            }
        }
    }
}
*/
@Composable
fun ListaIngredientes(
    ingredientes:List<IngredienteReceta>
) {
    LazyColumn(
        modifier = Modifier.padding(5.dp)
    ) {
        items(ingredientes) { ingrediente ->
            Row(
                modifier = Modifier.padding(6.dp)
                    .fillParentMaxWidth()
            )
            {
                Text(ingrediente.nombre)
                Text(ingrediente.cantidad)
            }
        }
    }
}


@Composable
fun ListaPasos(
    pasos:List<RecetaPaso>
) {
    LazyColumn(
        modifier = Modifier.padding(5.dp)
    ) {
        items(pasos) { paso ->
            Row(
                modifier = Modifier.padding(6.dp)
                    .fillParentMaxWidth()
            )
            {
                Text(paso.paso)
                Text(paso.comentarios)
            }
        }
    }
}