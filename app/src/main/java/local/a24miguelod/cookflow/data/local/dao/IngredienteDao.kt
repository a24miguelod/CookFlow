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

    /*
      SELECTS que devuelven Flow
     */

    @Query("SELECT * from Ingrediente order by nombre")
    fun getAllIngredientes(): Flow<List<IngredienteEntity>>  // room ya hace el emit

    @Query("SELECT * FROM Ingrediente WHERE ingredienteId IN (:ids) order by nombre")
    fun getIngredientesPorIds(ids: List<String>): Flow<List<IngredienteEntity>>

    @Query("SELECT * FROM Ingrediente WHERE enListaCompra = 1 order by nombre")
    fun getListaDeLaCompra(): Flow<List<IngredienteEntity>>

    /*
      BUSQUEDAS (suspend)
     */

    @Query("SELECT * FROM Ingrediente WHERE ingredienteId = :id")
    suspend fun getIngredienteById(id: String): IngredienteEntity?

    @Query("SELECT * FROM Ingrediente WHERE nombre = :nombre")
    suspend fun getIngredienteByNombre(nombre: String): IngredienteEntity?

    /*
      UPDATES (suspend)
     */

    @Query("UPDATE ingrediente SET enDespensa = :enDespensa WHERE ingredienteId = :id")
    suspend fun updateDisponibilidad(id: String, enDespensa: Boolean)

    @Query("UPDATE ingrediente SET enListaCompra = :enListaCompra WHERE ingredienteId = :id")
    suspend fun updateEnListaCompra(id: String, enListaCompra: Boolean)

    /*
      INSERTAR y borrar)
     */

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarIngrediente(ingrediente: IngredienteEntity)

    @Delete
    suspend fun delete(ingrediente: IngredienteEntity)
}