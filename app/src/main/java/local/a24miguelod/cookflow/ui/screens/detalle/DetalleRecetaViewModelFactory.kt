package local.a24miguelod.cookflow.ui.screens.detalle

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import local.a24miguelod.cookflow.data.repository.RecetasRepository
import local.a24miguelod.cookflow.ui.screens.lista.ListaRecetasViewModel

class DetalleRecetaViewModelFactory(
    private val owner: SavedStateRegistryOwner,
    private val repository: RecetasRepository
) : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return DetalleRecetaViewModel(repository, handle) as T
    }
}