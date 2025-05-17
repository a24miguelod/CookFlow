package local.a24miguelod.cookflow.presentation.screens.despensa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import local.a24miguelod.cookflow.data.repository.CacheRepository
import local.a24miguelod.cookflow.domain.model.Ingrediente

class DespensaViewModel(
    private val repository: CacheRepository,
) : ViewModel() {

    private val _ingredientes = MutableStateFlow<List<Ingrediente>>(emptyList())
    val ingredientes: StateFlow<List<Ingrediente>> = _ingredientes

    init {
        viewModelScope.launch {
            repository.getAllIngredientes()
                .collect { lista ->
                    _ingredientes.value = lista
                }
        }
    }

    fun toggleDisponible(ingrediente: Ingrediente) {
        viewModelScope.launch {
            val enDespensa = !ingrediente.enDespensa
            repository.setIngredienteDisponible(ingrediente.ingredienteId, enDespensa)
        }
    }

    fun anadirAlCarrito(ingrediente: Ingrediente) {
        viewModelScope.launch {
            //TODO
            //repository.anadirAListaCompra(ingrediente.ingrediente.id)
        }
    }
}

