package local.a24miguelod.cookflow.data.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import local.a24miguelod.cookflow.data.local.entities.Despensa
import local.a24miguelod.cookflow.data.local.entities.Ingrediente
import local.a24miguelod.cookflow.data.local.entities.ListaCompra

@Database(entities = [Ingrediente::class,Despensa::class, ListaCompra::class], version = 1)
abstract class CookFlowDatabase : RoomDatabase() {
    abstract fun DespensaDao(): DespensaDao
    abstract fun ListaCompraDao(): ListaCompraDao
}
