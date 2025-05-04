package local.a24miguelod.cookflow.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun DetalleRecetaScreen(idReceta: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.weight(2f))
        Text(text = "Detappe")
        Spacer(modifier = Modifier.weight(2f))
        Button(onClick ={}) {
            Text(text = "Ir al lista")
        }
    }

}