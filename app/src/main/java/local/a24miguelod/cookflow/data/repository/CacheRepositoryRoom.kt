package local.a24miguelod.cookflow.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import local.a24miguelod.cookflow.data.local.dao.IngredienteDao
import local.a24miguelod.cookflow.data.local.entities.IngredienteEntity
import local.a24miguelod.cookflow.data.local.mappers.toDomain
import local.a24miguelod.cookflow.data.local.mappers.toEntity
import local.a24miguelod.cookflow.domain.model.Ingrediente
import java.util.UUID

private const val TAG = "CacheRepositoryRoom"

class CacheRepositoryRoom(
    private val ingredienteDao: IngredienteDao
) : CacheRepository {

    override suspend fun insertIngrediente(ingrediente: Ingrediente) {
        ingredienteDao.insertarIngrediente(ingrediente.toEntity())
    }

    override suspend fun getIngredienteCacheado(ingrediente: Ingrediente): Ingrediente {
        var cacheIngrediente = ingredienteDao.getIngredienteByNombre(ingrediente.nombre)

        if (cacheIngrediente == null) {
            cacheIngrediente = IngredienteEntity(
                ingredienteId = if (ingrediente.ingredienteId.isEmpty())
                    UUID.randomUUID().toString()
                else
                    ingrediente.ingredienteId,
                nombre = ingrediente.nombre,
                enDespensa = false,
                enListaCompra = false
            )
            ingredienteDao.insertarIngrediente(cacheIngrediente)
        }
        return cacheIngrediente.toDomain()
    }

    override fun getIngredientes(lista: List<Ingrediente>): Flow<List<Ingrediente>> {
        TODO("Not yet implemented")
    }

    override fun getIngredientesPorIds(ids: List<String>): Flow<List<Ingrediente>> {
        return ingredienteDao
            .getIngredientesPorIds(ids)
            .map { list -> list.map { it.toDomain() } }
    }


    override suspend fun getIngredienteById(id: String): Ingrediente? {
        return ingredienteDao.getIngredienteById(id)?.toDomain()
    }

    override fun getAllIngredientes(): Flow<List<Ingrediente>> {
        return ingredienteDao.getAllIngredientes()
            .map { list -> list.map { it.toDomain() } }
    }

    override suspend fun setIngredienteDisponible(id: String, disponible: Boolean) {
        Log.d(TAG, "setIngredienteDisponible($id: String, $disponible: Boolean) {")
        ingredienteDao.updateDisponibilidad(id, disponible)
    }

    override suspend fun setIngredienteEnListaCompra(id: String, enListaCompra: Boolean) {
        Log.d(TAG, "update ingrediente $id a $enListaCompra")
        ingredienteDao.updateEnListaCompra(id, enListaCompra)
    }

    override fun getListaDeLaCompra(): Flow<List<Ingrediente>> {
        return ingredienteDao
            .getListaDeLaCompra()
            .map { list -> list.map { it.toDomain() } }
    }
}