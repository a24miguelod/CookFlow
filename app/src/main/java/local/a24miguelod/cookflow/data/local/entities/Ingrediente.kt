package local.a24miguelod.cookflow.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Ingrediente(
    @PrimaryKey
    val ingredienteId: Int,
    val nombre: String
)