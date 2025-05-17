package local.a24miguelod.cookflow.presentation.screens.lista_compra

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import local.a24miguelod.cookflow.data.repository.CacheRepository
import local.a24miguelod.cookflow.domain.model.Ingrediente

class ListaCompraViewModel(
    private val repository: CacheRepository,
) : ViewModel() {

    private val _ingredientes = MutableStateFlow<List<Ingrediente>>(emptyList())
    val ingredientes: StateFlow<List<Ingrediente>> = _ingredientes

    init {
        viewModelScope.launch {
            repository.getListaDeLaCompra()
                .collect { lista ->
                    _ingredientes.value = lista
                }
        }
    }

    fun eliminarDeListaDeLaCompra(ingrediente: Ingrediente) {
        viewModelScope.launch {
            repository.setIngredienteDisponible(ingrediente.ingredienteId, true)
            repository.setIngredienteEnListaCompra(ingrediente.ingredienteId, false)
        }
    }
}

