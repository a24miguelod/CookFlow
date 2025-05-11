package local.a24miguelod.cookflow.ui.screens.lista

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage


import local.a24miguelod.cookflow.R
import local.a24miguelod.cookflow.model.Receta

private const val TAG = "ListaRecestasScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaRecetasScreen(
    navegarAlDetalle: (Int) -> Unit,

) {



    Column(
        Modifier
            .fillMaxSize()
    ) {
        var expanded by remember { mutableStateOf(false) }

        TopAppBar(
            title = { Text(text = stringResource(R.string.app_name)) },


            actions = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Menú")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Configuración") },
                        onClick = { /* TODO */ }
                    )
                    DropdownMenuItem(
                        text = { Text("Cerrar sesión") },
                        onClick = { /* TODO */ }
                    )
                }
            }
        )


    }
}

@Composable
fun RecetaItem(receta: Receta) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            AsyncImage(
                model = receta.urlimagen,
                contentDescription = "Imagen de ${receta.nombre}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .height(120.dp)
                    .width(90.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = receta.nombre.orEmpty(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = receta.descripcion.orEmpty(),
                    fontSize = 14.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                RecetaBadge(true, {  }, listOf("Prueba","Otra"))
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecetaBadge(
    isFavorita: Boolean,
    onFavoritaClick: () -> Unit,
    tags: List<String>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icono de favorito
        IconButton(onClick = onFavoritaClick) {
            Icon(
                imageVector = if (isFavorita) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Marcar como favorita",
                tint = if (isFavorita) Color.Red else Color.Gray
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Tags como chips
        FlowRow(

            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tags.forEach { tag ->
                Surface(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .padding(end = 4.dp)
                ) {
                    Text(
                        text = tag,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }
}


//fun creaReceta() {
//    val db = Firebase.firestore
//    val random = (1..1000).random()
//    val receta = Receta(nombre = "Receta $random", descripcion = "Descripcion $random")
//    db.collection("recetas").add(receta)
//        .addOnSuccessListener {  Log.i(TAG, "Todo bien")}
//        .addOnFailureListener { Log.i(TAG, "Fallo al insertar")}
//        .addOnCompleteListener() { Log.i(TAG, "Insercionh completa") }
//}