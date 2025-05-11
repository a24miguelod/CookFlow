package local.a24miguelod.cookflow.ui.screens.lista

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import local.a24miguelod.cookflow.data.repository.RecetasRepository
import local.a24miguelod.cookflow.model.Receta
private const val TAG = "ListaRecestasScreen"

sealed class ListaRecetasState {
    data object Loading : ListaRecetasState()
    data class Success(
        val recetas: List<Receta>,
        val isLoading: Boolean = false
    ) : ListaRecetasState()
    data class Error(val message: String) : ListaRecetasState()
}

class ListaRecetasViewModel(
    private val repository:RecetasRepository
) : ViewModel() {

    private val _estado = MutableStateFlow<ListaRecetasState>(ListaRecetasState.Loading)
    val estado: StateFlow<ListaRecetasState> = _estado

    init {
        getRecetas()
    }

    private  fun getRecetas() {
        _estado.value = ListaRecetasState.Loading

        viewModelScope.launch {
            try {
                val recetas = repository.getRecetas()
                _estado.value = ListaRecetasState.Success(recetas, false)
            } catch (e:Exception) {
                Log.d(TAG,e.stackTraceToString())
            }
        }

    }


}