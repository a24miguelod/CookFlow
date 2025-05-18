package local.a24miguelod.cookflow.common.dependencies

import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.firebase.firestore.FirebaseFirestore
import io.ktor.client.HttpClient
import local.a24miguelod.cookflow.data.repository.CacheRepository
import local.a24miguelod.cookflow.data.repository.RecetasRepository

interface AppInyectorContainer {
    val recetasRepository: RecetasRepository
    val cacheRepository: CacheRepository
    val roomDatabase:RoomDatabase
    val httpCliente: HttpClient
    val firebaseDatabase: FirebaseFirestore
}