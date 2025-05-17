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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import local.a24miguelod.cookflow.domain.model.Ingrediente
import local.a24miguelod.cookflow.presentation.screens.lista.BotonConIconoYTexto

@Composable
fun DespensaScreen(
    viewModel: DespensaViewModel,
    onToggleDespensa: (Ingrediente) -> Unit,
    onAnadirAlCarrito: (Ingrediente) -> Unit
) {

    val ingredientes by viewModel.ingredientes.collectAsState()

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
                        onClick = { }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
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

