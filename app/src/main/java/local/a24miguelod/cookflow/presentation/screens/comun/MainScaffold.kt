package local.a24miguelod.cookflow.presentation.screens.comun

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import local.a24miguelod.cookflow.R
import local.a24miguelod.cookflow.presentation.screens.lista.BotonConIconoYTexto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CookFlowScaffold(
    onDespensaClick: () -> Unit,
    onListaCompraClick: () -> Unit,
    onHomeClick: () -> Unit,
    floatingActionButton: (@Composable () -> Unit)? = null, //  FAB opcional
    content: @Composable (PaddingValues) -> Unit,
) {

    Scaffold(
        floatingActionButton = {
            floatingActionButton?.invoke() // 👈 Solo lo pinta si no es null
        },
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(text = stringResource(R.string.app_name),
                        color = MaterialTheme.colorScheme.onPrimaryContainer)
                },
                navigationIcon = {
                    IconButton(onClick = onHomeClick ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_chef),
                            contentDescription = stringResource(R.string.app_name),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        BotonConIconoYTexto(
                            icon = Icons.Default.Home,
                            text = "Recetas",
                            onClick = { onHomeClick() },
                            modifier = Modifier.weight(1f)
                        )
                        BotonConIconoYTexto(
                            icon = Icons.AutoMirrored.Filled.List,
                            text = "Despensa",
                            onClick = { onDespensaClick() },
                            modifier = Modifier.weight(1f)
                        )
                        BotonConIconoYTexto(
                            icon = Icons.Default.ShoppingCart,
                            text = "Lista de la compra",
                            onClick = { onListaCompraClick() },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }


            )

        }

    ) // cierra scaffold

    { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            content(paddingValues)
        }
    }
}