package local.a24miguelod.cookflow.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage


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
    ) {
        Spacer(modifier = Modifier.height(33.dp))
        Text(
            "A ver que pasa",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.height(33.dp)
        )
        LazyColumn (modifier = Modifier.fillMaxSize()){
            items(recetas.value) { receta ->
                RecetaItem(receta)
            }
        }
    }
}

@Composable
fun RecetaItem(receta: Receta) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            AsyncImage(
                model = receta.urlimagen,
                contentDescription = "Imagen de ${receta.nombre}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp)).height(90.dp).width(90.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = receta.nombre.orEmpty(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = receta.descripcion.orEmpty(),
                    fontSize = 14.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
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