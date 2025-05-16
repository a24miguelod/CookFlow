package local.a24miguelod.cookflow.ui.screens.lista

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import local.a24miguelod.cookflow.data.repository.RecetasRepository


class ListaRecetasViewModelFactory(
    owner: SavedStateRegistryOwner,
    private val repository: RecetasRepository
) : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return ListaRecetasViewModel(repository, handle) as T
    }
}