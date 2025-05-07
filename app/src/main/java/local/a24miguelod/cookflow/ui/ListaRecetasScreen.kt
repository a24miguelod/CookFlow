package local.a24miguelod.cookflow.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import local.a24miguelod.cookflow.model.Receta

private const val TAG = "ListaRecestasScreen"

@Composable
fun ListaRecetasScreen(
    navegarAlDetalle: (Int) -> Unit,
    viewModel: RecetasViewModel) {

    val recetas: State<List<Receta>> = viewModel.recetas.collectAsState()
    Column(
        Modifier
            .fillMaxSize()
            .background(Black)
    ) {
        Text(
            "A ver que pasa",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.padding(16.dp)
        )


        LazyRow {
            items(recetas.value) { receta ->
                Text(
                    text = receta.nombre.orEmpty(), // Asumiendo que `Receta` tiene una propiedad `nombre`
                    color = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

    }
}

@Composable
fun RecetaItem(receta: Receta) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = receta.nombre.orEmpty(), color = Color.White)
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