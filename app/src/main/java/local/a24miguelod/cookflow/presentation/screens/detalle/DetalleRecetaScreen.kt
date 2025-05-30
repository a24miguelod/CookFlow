package local.a24miguelod.cookflow.presentation.screens.detalle

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PlayArrow

import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShoppingCart

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import local.a24miguelod.cookflow.R
import local.a24miguelod.cookflow.domain.model.IngredienteReceta
import local.a24miguelod.cookflow.domain.model.Receta
import local.a24miguelod.cookflow.domain.model.RecetaPaso
import local.a24miguelod.cookflow.presentation.screens.comun.CargandoGenerico
import local.a24miguelod.cookflow.presentation.screens.comun.ErrorGenerico
import local.a24miguelod.cookflow.presentation.screens.comun.formatDuracion
import local.a24miguelod.cookflow.presentation.screens.lista.ListaRecetasUIState

private const val TAG = "DetalleRecetaScreen"

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetalleRecetaScreen(
    viewModel: DetalleRecetaViewModel,
    onFlowClick: (Receta) -> Unit,
    onToggleDespensa: (IngredienteReceta) -> Unit,
    onAnadirAListaCompra: (IngredienteReceta) -> Unit,
) {

    // Sin poner esto, las modificaciones en la despensa o lista compra
    // no se reflejan al volver para atras
    LaunchedEffect(true) {
        viewModel.updateUI()
    }

    val estado by viewModel.estado.collectAsState()

    when (estado) {

        is DetalleRecetaUIState.Error -> {
            val errorState = estado as DetalleRecetaUIState.Error
            Log.d(TAG, errorState.message)
            ErrorGenerico((estado as DetalleRecetaUIState.Error).message)
        }

        is DetalleRecetaUIState.Loading -> {
            Log.d(TAG, stringResource(R.string.recuperando_datos))
            CargandoGenerico(stringResource(R.string.recuperando_datos))
        }

        is DetalleRecetaUIState.Success -> {
            val sucessState = estado as DetalleRecetaUIState.Success
            val receta = sucessState.receta

            Box(modifier = Modifier.fillMaxSize()) {

                LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                    item {

                        AsyncImage(
                            model = receta.urlimagen,
                            contentDescription = receta.descripcion,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                        )
                        Text(
                            receta.nombre,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            modifier = Modifier.padding(12.dp),

                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 24.sp,
                                lineHeight = 1.5.em
                            )
                        )

                        HorizontalDivider()
                        Text(
                            receta.descripcion,
                            modifier = Modifier.padding(12.dp)
                        )
                        HorizontalDivider()
                        ListaIngredientes(
                            ingredientes = receta.ingredientes,

                            onToggleDespensa = onToggleDespensa,
                            onAnadirAListaCompra = onAnadirAListaCompra
                        )
                        HorizontalDivider()
                        ListaPasos(receta.pasos)


                    }

                }
                FloatingActionButton(
                    onClick = { onFlowClick(receta) },
                    containerColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp) // separa del borde
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Empezar pasos"
                    )
                }

            }
        }
    }

}

@Composable
fun ListaIngredientes(
    ingredientes: List<IngredienteReceta>,
    onToggleDespensa: (IngredienteReceta) -> Unit,
    onAnadirAListaCompra: (IngredienteReceta) -> Unit
) {
    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        ingredientes.forEach { ingrediente ->

            Row(
                modifier = Modifier
                    .padding(0.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                IconButton(onClick = { onToggleDespensa(ingrediente) }) {
                    Icon(
                        imageVector = if (ingrediente.ingrediente.enDespensa)
                            Icons.Default.Check
                        else
                            Icons.Default.Clear,
                        contentDescription = "Disponibilidad",
                        tint = if (ingrediente.ingrediente.enDespensa) Color.Green else Color.Red,
                        modifier = Modifier
                            .size(16.dp)


                    )
                }

                Column(
                    modifier = Modifier.weight(1f)

                ) {
                    Text(
                        ingrediente.ingrediente.nombre,
                        fontSize = 16.sp
                    )
                    Text(
                        ingrediente.cantidad,
                        fontSize = 12.sp
                    )
                }
                IconButton(onClick = { onAnadirAListaCompra(ingrediente) }) {
                    Icon(
                        imageVector = if (ingrediente.ingrediente.enListaCompra)
                            Icons.Default.Clear
                        else
                            Icons.Default.ShoppingCart,

                        tint = if (ingrediente.ingrediente.enListaCompra) Color.Gray else Color.Black,
                        contentDescription = "Añadir a la compra",
                        modifier = Modifier.size(16.dp)
                    )
                }

            }
        }
    }
}


@Composable
fun ListaPasos(
    pasos: List<RecetaPaso>
) {
    Column(
        modifier = Modifier.padding(5.dp)
    ) {
        pasos.forEach { paso ->
            Column(
                modifier = Modifier
                    .padding(12.dp)
            )
            {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        paso.resumen,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Duracion",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(26.dp)

                    )
                    Text(
                        formatDuracion(paso.duracion),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                }

                Text(
                    paso.detallelargo,
                    fontSize = 16.sp,
                )
            }
        }
    }
}

@Preview
@Composable
fun ListaPasos(
) {

    val pasos = listOf(
        RecetaPaso(
            resumen = "Preparar la masa",
            detallelargo = "Mezclar harina, levadura, sal y agua hasta obtener una masa homogénea. Dejar reposar 1h.",
            duracion = 60f
        ),
        RecetaPaso(
            resumen = "Extender la masa",
            detallelargo = "Formar una base de pizza fina con la masa reposada.",
            duracion = 10f
        ),
        RecetaPaso(
            resumen = "Añadir ingredientes",
            detallelargo = "Cubrir con tomate triturado, mozzarella, albahaca y un chorrito de aceite.",
            duracion = 5f
        ),
        RecetaPaso(
            resumen = "Hornear",
            detallelargo = "Hornear a 220°C durante 15 minutos o hasta que la masa esté crujiente.",
            duracion = 15f
        )
    )

    LazyColumn(
        modifier = Modifier.padding(5.dp)
    ) {
        items(pasos) { paso ->
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillParentMaxWidth()
            )
            {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        paso.resumen,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Lo tengo",
                        tint = Color.Green, // else Color.Gray

                    )
                    Text(
                        paso.duracion.toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                }

                Text(
                    paso.detallelargo,
                    fontSize = 16.sp,
                )
            }
        }
    }
}
