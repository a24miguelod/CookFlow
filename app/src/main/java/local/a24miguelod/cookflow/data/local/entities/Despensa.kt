package local.a24miguelod.cookflow.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = IngredienteEntity::class,
            parentColumns = ["ingredienteId"],
            childColumns = ["ingredienteId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )]
)
data class Despensa(
    @PrimaryKey
    val ingredienteId: String,
    val disponible: Boolean = false
)