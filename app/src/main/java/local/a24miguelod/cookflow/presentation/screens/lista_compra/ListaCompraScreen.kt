package local.a24miguelod.cookflow.presentation.screens.despensa

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ShoppingCart

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import local.a24miguelod.cookflow.domain.model.Ingrediente
import local.a24miguelod.cookflow.presentation.screens.comun.CookFlowScaffold
import local.a24miguelod.cookflow.presentation.screens.lista_compra.ListaCompraViewModel


@Composable
fun ListaCompraScreen(
    viewModel: ListaCompraViewModel,
    onEliminarDeListaDeLaCompra: (Ingrediente) -> Unit,
    onDespensaClick: () -> Unit,
    onHomeClick: () -> Unit,
    onListaCompraClick: () -> Unit
) {

    val ingredientes by viewModel.ingredientes.collectAsState()

    CookFlowScaffold (
        onDespensaClick = onDespensaClick,
        onListaCompraClick = onListaCompraClick,
        onHomeClick = onHomeClick
    ) { paddingValues ->

        LazyColumn(
        ) {
            items(ingredientes) { ingrediente ->

                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = ingrediente.nombre,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Spacer(modifier = Modifier.width(8.dp))

                        IconButton(
                            onClick = { onEliminarDeListaDeLaCompra(ingrediente) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
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
    }}


