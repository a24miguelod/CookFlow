package local.a24miguelod.cookflow.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import local.a24miguelod.cookflow.data.local.entities.IngredienteEntity

@Dao
interface IngredienteDao {

    @Query("SELECT * from Ingrediente")
    fun getIngredientes(): Flow<List<IngredienteEntity>>  // room ya hace el emit

    @Query("SELECT * FROM Ingrediente WHERE ingredienteId = :id")
    fun getIngredienteById(id: String): IngredienteEntity?

    @Query("UPDATE ingrediente SET enDespensa = :enDespensa WHERE ingredienteId = :id")
    fun updateDisponibilidad(id: String, enDespensa: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ingrediente: IngredienteEntity)

    @Delete
    fun delete(ingrediente: IngredienteEntity)
}