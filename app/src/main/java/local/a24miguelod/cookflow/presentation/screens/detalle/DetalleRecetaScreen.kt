package local.a24miguelod.cookflow.presentation.screens.detalle

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import local.a24miguelod.cookflow.domain.model.IngredienteReceta
import local.a24miguelod.cookflow.domain.model.Receta
import local.a24miguelod.cookflow.domain.model.RecetaPaso

private const val TAG = "DetalleRecetaScreen"

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetalleRecetaScreen(
    viewModel:DetalleRecetaViewModel,
    onFlowClick: (Receta) -> Unit,
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
            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                item {
                    Log.d(TAG, "Estoy en el composable ${receta.toString()}")
                    AsyncImage(
                        model = receta.urlimagen,
                        contentDescription = receta.descripcion,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                    Text(
                        receta.nombre,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(8.dp),

                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 24.sp,
                            lineHeight = 1.5.em
                        )
                    )

                HorizontalDivider()
                Text(
                    receta.descripcion,
                    modifier = Modifier.padding(8.dp)
                )
                HorizontalDivider()
                ListaIngredientes(receta.ingredientes)
                HorizontalDivider()
                ListaPasos(receta.pasos)

                Button(onClick = { onFlowClick(receta) }) {
                    Text(text = "Empezar!")
                }
            }

        }
    }
}
}
/*
@Composable
fun DetalleRecetaScreenPreview(

) {

    val receta = Receta(
        uuidReceta = "receta-001",
        nombre = "Pizza Casera Margarita",
        descripcion = "Una receta sencilla para preparar una pizza casera deliciosa con tomate, mozzarella y albahaca.",
        ingredientes = listOf(
            IngredienteReceta(
                ingredienteId = "ing-001",
                nombre = "Harina de trigo",
                cantidad = "500g"
            ),
            IngredienteReceta(
                ingredienteId = "ing-002",
                nombre = "Agua",
                cantidad = "300ml"
            ),
            IngredienteReceta(
                ingredienteId = "ing-003",
                nombre = "Levadura seca",
                cantidad = "7g"
            ),
            IngredienteReceta(
                ingredienteId = "ing-004",
                nombre = "Sal",
                cantidad = "1 cucharadita"
            ),
            IngredienteReceta(
                ingredienteId = "ing-005",
                nombre = "Tomate triturado",
                cantidad = "200ml"
            ),
            IngredienteReceta(
                ingredienteId = "ing-006",
                nombre = "Mozzarella",
                cantidad = "200g"
            ),
            IngredienteReceta(
                ingredienteId = "ing-007",
                nombre = "Hojas de albahaca",
                cantidad = "Unas cuantas"
            ),
            IngredienteReceta(
                ingredienteId = "ing-008",
                nombre = "Aceite de oliva",
                cantidad = "2 cucharadas"
            )
        ),
        pasos = listOf(
            Paso(
                resumen = "Preparar la masa",
                detallelargo = "Mezclar harina, levadura, sal y agua hasta obtener una masa homogénea. Dejar reposar 1h.",
                duracion = 60f
            ),
            Paso(
                resumen = "Extender la masa",
                detallelargo = "Formar una base de pizza fina con la masa reposada.",
                duracion = 10f
            ),
            Paso(
                resumen = "Añadir ingredientes",
                detallelargo = "Cubrir con tomate triturado, mozzarella, albahaca y un chorrito de aceite.",
                duracion = 5f
            ),
            Paso(
                resumen = "Hornear",
                detallelargo = "Hornear a 220°C durante 15 minutos o hasta que la masa esté crujiente.",
                duracion = 15f
            )
        ),
        urlimagen = "https://ejemplo.com/imagenes/pizza_margarita.jpg"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {

        Text(
            receta.nombre,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(8.dp)
        )
        HorizontalDivider()
        Text(
            receta.descripcion,
            modifier = Modifier.padding(8.dp)
        )
        HorizontalDivider()
        ListaIngredientes(receta.ingredientes)
        //ListaPasos(receta.pasos)


    }
}
*/
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
    ingredientes: List<IngredienteReceta>
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
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Lo tengo",
                        tint = Color.Green, // else Color.Gray
                        modifier = Modifier
                            .size(16.dp)
                            .weight(1f)

                    )
                }

                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "No lo tengo",
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(16.dp)
                            .weight(1f)
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
                        ingrediente.cantidad.toString(),
                        fontSize = 12.sp
                    )
                }
                // Botón para añadir a la compra
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
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
