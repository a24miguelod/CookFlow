package local.a24miguelod.cookflow.presentation.screens.lista

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
import androidx.lifecycle.createSavedStateHandle
import kotlinx.coroutines.flow.onStart
import local.a24miguelod.cookflow.domain.model.Receta

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
) : ViewModel() {

    private val _estado = MutableStateFlow<ListaRecetasUIState>(ListaRecetasUIState.Loading)
    val estado: StateFlow<ListaRecetasUIState> = _estado

    init {
        getRecetasConFlow()
    }
/*
    private fun getRecetas() {
        _estado.value = ListaRecetasUIState.Loading

        viewModelScope.launch {
            try {
                val recetas = repository.getRecetasConFlow()
                _estado.value = ListaRecetasUIState.Success(recetas, false)
                Log.d(TAG, recetas[0].toString())
            } catch (e:Exception) {
                _estado.value = ListaRecetasUIState.Error("No se pudieron cargar las recetas")
                Log.d(TAG,e.stackTraceToString())
            }
        }

    }
*/
    private fun getRecetasConFlow() {

        Log.d(TAG, "getRecetasConFlow")
        _estado.value = ListaRecetasUIState.Loading

        viewModelScope.launch {
            repository.getRecetasConFlow()
                .onStart { _estado.value = ListaRecetasUIState.Loading }
                .collect { listaParcial ->
                    _estado.value = ListaRecetasUIState.Success(listaParcial, false)
                }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val repository = CookFlowApp.contenedor.recetasRepository
                val savedStateHandle = createSavedStateHandle()
                ListaRecetasViewModel(
                    repository = repository,
                )
            }
        }
    }
}

