package local.a24miguelod.cookflow.presentation.screens.despensa

import androidx.collection.emptyLongSet
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ShoppingCart

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import local.a24miguelod.cookflow.domain.model.Ingrediente
import local.a24miguelod.cookflow.presentation.screens.comun.CookFlowScaffold
import local.a24miguelod.cookflow.presentation.screens.comun.Titulo


@Composable
fun DespensaScreen(
    viewModel: DespensaViewModel,
    onToggleDespensa: (Ingrediente) -> Unit,
    onAnadirAlCarrito: (Ingrediente) -> Unit,
    onDespensaClick: () -> Unit,
    onHomeClick: () -> Unit,
    onListaCompraClick: () -> Unit,
) {

    val ingredientes by viewModel.ingredientes.collectAsState()

    CookFlowScaffold(
        onDespensaClick = onDespensaClick,
        onListaCompraClick = onListaCompraClick,
        onHomeClick = onHomeClick
    ) { paddingValues ->

        Column() {
            Titulo("Despensa")
            LazyColumn(

            ) {
                items(ingredientes) { ingrediente ->

                    Row(
                        modifier = Modifier
                            .padding(6.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = ingrediente.nombre,
                                color = if (ingrediente.enDespensa) Color.Black else Color.Gray,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Switch de disponible
                            Switch(
                                checked = ingrediente.enDespensa,
                                onCheckedChange = { onToggleDespensa(ingrediente) }
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            IconButton(
                                onClick = { onAnadirAlCarrito(ingrediente) }
                            ) {
                                Icon(
                                    imageVector = if (ingrediente.enListaCompra)
                                        Icons.Default.ShoppingCart
                                    else Icons.Default.Clear,
                                    contentDescription = "Comprar",
                                    //tint = if (ingrediente.ingrediente.enDespensa) Color.Green else Color.Gray,
                                    modifier = Modifier
                                        .size(16.dp)
                                        .weight(1f)

                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

