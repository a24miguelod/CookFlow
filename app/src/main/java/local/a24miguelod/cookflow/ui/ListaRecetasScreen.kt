package local.a24miguelod.cookflow.ui

import android.util.Log
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

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "ListaRecestasScreen"
@Composable
fun ListaRecetasScreen(navegarAlDetalle:(Int) -> Unit) {

    var idReceta by remember { mutableIntStateOf(0) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.weight(2f))
        Text(text = "Buenos dias")
        Spacer(modifier = Modifier.weight(2f))
        Button(onClick = {
            creaReceta()
        }) {
            Text("Crea receta")
        }
        Spacer(modifier = Modifier.weight(2f))
        Spacer(modifier = Modifier.weight(2f))
        Button(onClick ={
            throw Exception("Crash de ejemplo")
        }) {
            Text(text = "Crashear")
        }
        Spacer(modifier = Modifier.weight(2f))

        Button(onClick ={
            navegarAlDetalle(idReceta)
        }) {
            Text(text = "Ir al detallee")
        }
    }

}

data class Receta(

    val nombre:String,
    val descripcion: String
)

fun creaReceta() {
    val db = Firebase.firestore
    val random = (1..1000).random()
    val receta = Receta(nombre = "Receta $random", descripcion = "Descripcion $random")
    db.collection("recetas").add(receta)
        .addOnSuccessListener {  Log.i(TAG, "Todo bien")}
        .addOnFailureListener { Log.i(TAG, "Fallo al insertar")}
        .addOnCompleteListener() { Log.i(TAG, "Insercionh completa") }
}