package local.a24miguelod.cookflow.ui.screens.lista

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import local.a24miguelod.cookflow.CookFlowApp
import local.a24miguelod.cookflow.data.repository.RecetasRepository
import local.a24miguelod.cookflow.model.Receta
import androidx.lifecycle.createSavedStateHandle

private const val TAG = "ListaRecestasScreen"

sealed class ListaRecetasUIState {
    data object Loading : ListaRecetasUIState()
    data class Success(
        val recetas: List<Receta>,
        val isLoading: Boolean = false
    ) : ListaRecetasUIState()
    data class Error(val message: String) : ListaRecetasUIState()
}

class ListaRecetasViewModel(
    private val repository:RecetasRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _estado = MutableStateFlow<ListaRecetasUIState>(ListaRecetasUIState.Loading)
    val estado: StateFlow<ListaRecetasUIState> = _estado

    init {
        getRecetas()
    }

    private fun getRecetas() {
        _estado.value = ListaRecetasUIState.Loading

        viewModelScope.launch {
            try {
                val recetas = repository.getRecetas()
                _estado.value = ListaRecetasUIState.Success(recetas, false)
                Log.d(TAG, recetas[0].toString())
            } catch (e:Exception) {
                _estado.value = ListaRecetasUIState.Error("No se pudieron cargar las recetas")
                Log.d(TAG,e.stackTraceToString())
            }
        }

    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CookFlowApp)
                val repository = application.contenedor.recetasRepository
                val savedStateHandle = createSavedStateHandle()
                ListaRecetasViewModel(
                    repository = repository,
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }
}

