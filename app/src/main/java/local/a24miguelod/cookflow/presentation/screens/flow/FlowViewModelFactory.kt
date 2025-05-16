package local.a24miguelod.cookflow.presentation.screens.flow

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import local.a24miguelod.cookflow.data.repository.RecetasRepository


class FlowViewModelFactory(
    private val owner: SavedStateRegistryOwner,
    private val repository: RecetasRepository
) : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return FlowViewModel(repository, handle) as T
    }
}