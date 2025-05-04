package local.a24miguelod.cookflow.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ListaRecetasScreen(navegarAlDetalle:(Int) -> Unit) {
    var idReceta by remember { mutableIntStateOf(0) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.weight(2f))
        Text(text = "Buenos dias")
        Spacer(modifier = Modifier.weight(2f))
        Button(onClick ={
            navegarAlDetalle(idReceta)
        }) {
            Text(text = "Ir al deta;;e")
        }
    }

}
