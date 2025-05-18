package local.a24miguelod.cookflow.presentation.screens.lista

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.automirrored.filled.List

import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home

import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import local.a24miguelod.cookflow.CookFlowApp

import local.a24miguelod.cookflow.domain.model.Receta
import local.a24miguelod.cookflow.presentation.screens.comun.CargandoGenerico
import local.a24miguelod.cookflow.presentation.screens.comun.CookFlowScaffold
import local.a24miguelod.cookflow.presentation.screens.comun.ErrorGenerico

private const val TAG = "ListaRecestasScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaRecetasScreen(
    viewModel: ListaRecetasViewModel,
    onRecetaClick: (Receta) -> Unit,
    onDespensaClick: () -> Unit,
    onHomeClick: () -> Unit,
    onListaCompraClick: () -> Unit,
) {

    val estado by viewModel.estado.collectAsState()

    // https://developer.android.com/develop/ui/compose/components/scaffold?hl=es-419

    CookFlowScaffold(
        onDespensaClick = onDespensaClick,
        onListaCompraClick = onListaCompraClick,
        onHomeClick = onHomeClick
    ) { paddingValues ->
        when (estado) {
            is ListaRecetasUIState.Error -> {
                ErrorGenerico((estado as ListaRecetasUIState.Error).message)
            }

            is ListaRecetasUIState.Loading -> {
                CargandoGenerico("Obteniendo recetas")
            }

            is ListaRecetasUIState.Success -> {
                val successState = estado as ListaRecetasUIState.Success
                ListaRecetasContent(
                    onRecetaClick = onRecetaClick,
                    recetas = successState.recetas
                )
            }

        }
    }


}

@Composable
fun ListaRecetasContent(
    recetas: List<Receta>,
    onRecetaClick: (Receta) -> Unit

) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
        ) {
            items(recetas) { receta ->
                RecetaItem(
                    receta = receta,
                    onRecetaClick = onRecetaClick
                )
            }
        }
    }
}


@Composable
fun RecetaItem(
    receta: Receta,
    onRecetaClick: (Receta) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onRecetaClick(receta) }
            .border(
                width = 2.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(16.dp)
            ),

        shape = RoundedCornerShape(16.dp),

        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
        ) {
            AsyncImage(
                model = receta.urlimagen,
                contentDescription = receta.descripcion,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .height(120.dp)
                    .width(120.dp)
                    .padding(5.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = receta.nombre.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = receta.descripcion.toString(),
                    fontSize = 14.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                //RecetaBadge(true, { }, listOf("Prueba", "Otra"))
            }
        }
    }
}


@Composable
fun BotonConIconoYTexto(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(4.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,

            )
        Text(
            text = text,
            fontSize = 12.sp,
            modifier = Modifier.weight(1f)
        )
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