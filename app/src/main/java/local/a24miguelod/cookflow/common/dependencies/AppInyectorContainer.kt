package local.a24miguelod.cookflow.common.dependencies

import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.firebase.firestore.FirebaseFirestore
import local.a24miguelod.cookflow.data.repository.RecetasRepository

interface AppInyectorContainer {
    val recetasRepository: RecetasRepository
    val roomDatabase:RoomDatabase
    val firebaseDatabase: FirebaseFirestore
}