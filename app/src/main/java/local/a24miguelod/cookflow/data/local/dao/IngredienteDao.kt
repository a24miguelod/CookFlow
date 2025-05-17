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
    fun getAllIngredientes(): Flow<List<IngredienteEntity>>  // room ya hace el emit

    @Query("SELECT * FROM Ingrediente WHERE ingredienteId = :id")
    suspend fun getIngredienteById(id: String): IngredienteEntity?

    @Query("SELECT * FROM Ingrediente WHERE nombre = :nombre")
    suspend fun getIngredienteByNombre(nombre: String): IngredienteEntity?

    @Query("UPDATE ingrediente SET enDespensa = :enDespensa WHERE ingredienteId = :id")
    suspend fun updateDisponibilidad(id: String, enDespensa: Boolean)

    @Query("UPDATE ingrediente SET enListaCompra = :enListaCompra WHERE ingredienteId = :id")
    suspend fun updateEnListaCompra(id: String, enListaCompra: Boolean)

    @Query("SELECT * FROM Ingrediente WHERE ingredienteId IN (:ids)")
    fun getIngredientesPorIds(ids: List<String>): Flow<List<IngredienteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ingrediente: IngredienteEntity)

    @Delete
    suspend fun delete(ingrediente: IngredienteEntity)
}