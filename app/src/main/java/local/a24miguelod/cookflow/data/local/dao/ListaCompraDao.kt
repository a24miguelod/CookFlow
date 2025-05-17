package local.a24miguelod.cookflow.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import local.a24miguelod.cookflow.data.local.entities.Ingrediente

@Dao
interface ListaCompraDao {

    @Query("SELECT i.ingredienteId, nombre " +
            "FROM ListaCompra l JOIN INGREDIENTE i" +
            "  ON l.ingredienteId = i.ingredienteId ")
    fun getDespensa(): List<Ingrediente>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ingrediente: Ingrediente)

    @Delete
    fun delete(ingrediente: Ingrediente)
}