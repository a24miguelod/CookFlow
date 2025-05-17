package local.a24miguelod.cookflow.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import local.a24miguelod.cookflow.data.local.entities.IngredienteEntity

@Dao
interface IngredienteDao {

    @Query("SELECT * from Ingrediente")
    fun getIngredientes(): List<IngredienteEntity>

    @Query("SELECT * FROM Ingrediente WHERE ingredienteId = :id")
    fun getIngredienteById(id: Int): IngredienteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ingrediente: IngredienteEntity)

    @Delete
    fun delete(ingrediente: IngredienteEntity)
}