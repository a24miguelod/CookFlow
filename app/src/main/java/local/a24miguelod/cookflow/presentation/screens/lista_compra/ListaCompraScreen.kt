package local.a24miguelod.cookflow.presentation.screens.despensa

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Check

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import local.a24miguelod.cookflow.R
import local.a24miguelod.cookflow.domain.model.Ingrediente
import local.a24miguelod.cookflow.presentation.screens.comun.Titulo
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

    Column(
        verticalArrangement = Arrangement.Top
    ) {
        Titulo(stringResource(R.string.lista_de_la_compra))
        if (ingredientes.isEmpty()) {
            // Estado vacío
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Lista vacía!",
                    fontSize = 30.sp,
                )
            }
        } else
            LazyColumn(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.fillMaxSize()
            ) {
                items(ingredientes) { ingrediente ->

                    Row(
                        modifier = Modifier
                            .padding(1.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = ingrediente.nombre,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        Row(verticalAlignment = Alignment.Top) {

                            Spacer(modifier = Modifier.width(8.dp))

                            IconButton(
                                onClick = { onEliminarDeListaDeLaCompra(ingrediente) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = stringResource(R.string.comprar),
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


