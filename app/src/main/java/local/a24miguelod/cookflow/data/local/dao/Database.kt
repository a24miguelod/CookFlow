package local.a24miguelod.cookflow.data.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import local.a24miguelod.cookflow.data.local.entities.IngredienteEntity


@Database(entities = [IngredienteEntity::class], version = 1)
abstract class CookFlowDatabase : RoomDatabase() {
    abstract fun ingredienteDao(): IngredienteDao
}
