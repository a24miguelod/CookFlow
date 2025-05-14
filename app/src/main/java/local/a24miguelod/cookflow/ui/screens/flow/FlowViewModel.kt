package local.a24miguelod.cookflow.ui.screens.flow

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import local.a24miguelod.cookflow.CockFlowDestinationsArgs
import local.a24miguelod.cookflow.CookFlowApp
import local.a24miguelod.cookflow.data.repository.RecetasRepository
import local.a24miguelod.cookflow.domain.model.Receta
import local.a24miguelod.cookflow.ui.screens.detalle.DetalleRecetaUIState
import local.a24miguelod.cookflow.ui.screens.detalle.DetalleRecetaViewModel


private const val TAG = "FlowViewModel"

sealed class FlowRecetasUIState {
    data object Loading : FlowRecetasUIState()
    data class Success(
        val receta: Receta,
        val isLoading: Boolean = false
    ) : FlowRecetasUIState()
    data class Error(val message: String) : FlowRecetasUIState()
}

class FlowViewModel(
    private val repository: RecetasRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _estado = MutableStateFlow<FlowRecetasUIState>(FlowRecetasUIState.Loading)
    val estado: StateFlow<FlowRecetasUIState> = _estado

    private val recetaUuid:String = savedStateHandle[CockFlowDestinationsArgs.RECETA_ID]!!

    init {
        val receta = getReceta(recetaUuid)
    }

    private fun getReceta(uuidReceta: String) {
        _estado.value = FlowRecetasUIState.Loading

        viewModelScope.launch {
            try {
                val receta = repository.getReceta(uuidReceta)?.let { receta ->
                    _estado.value = FlowRecetasUIState.Success(receta, false)
                } ?: run {
                    _estado.value = FlowRecetasUIState.Error("La receta ${uuidReceta} no existe")
                }
            } catch (e:Exception) {
                _estado.value = FlowRecetasUIState.Error("No se pudo cargar la receta")
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
                DetalleRecetaViewModel(repository = repository,
                    savedStateHandle = savedStateHandle)
            }
        }
    }
}

