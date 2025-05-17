package local.a24miguelod.cookflow.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

// El nombre termina en Entity para facilitar la lectura en los mappers
@Entity(tableName = "ingrediente")
data class IngredienteEntity(
    @PrimaryKey
    val ingredienteId: String,
    val nombre: String,
    val enDespensa: Boolean = false,
    val enListaCompra: Boolean = false
)