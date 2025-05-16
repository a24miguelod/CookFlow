package local.a24miguelod.cookflow.presentation.screens.flow

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import local.a24miguelod.cookflow.presentation.navigation.CockFlowDestinationsArgs
import local.a24miguelod.cookflow.CookFlowApp
import local.a24miguelod.cookflow.data.repository.RecetasRepository
import local.a24miguelod.cookflow.domain.model.Receta


private const val TAG = "FlowViewModel"

sealed class FlowRecetasUIState {
    data object Loading : FlowRecetasUIState()
    data class Success(
        val receta: Receta,
        val isLoading: Boolean = false,
        val progreso: Float = 0f,
        val pasoActual: Int = 0
    ) : FlowRecetasUIState()

    data class Error(val message: String) : FlowRecetasUIState()
}

class FlowViewModel(
    private val repository: RecetasRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _estado = MutableStateFlow<FlowRecetasUIState>(FlowRecetasUIState.Loading)
    val estado: StateFlow<FlowRecetasUIState> = _estado

    private val recetaUuid: String = savedStateHandle[CockFlowDestinationsArgs.RECETA_ID]!!

    init {
        Log.d(TAG, "savedStateHandle $recetaUuid")
        getReceta(recetaUuid)
    }

    private fun getReceta(uuidReceta: String) {
        _estado.value = FlowRecetasUIState.Loading

        viewModelScope.launch {
            try {
                val receta = repository.getReceta(uuidReceta)?.let { receta ->

                    //_estado.value = FlowRecetasUIState.Success(receta, false)

                    val startTime = System.currentTimeMillis()
                    var pasoActual = 0;

                    while (pasoActual < receta.pasos.size) {
                        val transcurrido = System.currentTimeMillis() - startTime
                        val pasoObjectActual = receta.pasos[pasoActual]
                        val progreso =
                            (transcurrido.toFloat() / (pasoObjectActual.duracion*60000f)).coerceIn(0f, 1f)
                        if (progreso > 1f) {
                            pasoActual += 1
                        }
                        _estado.value = FlowRecetasUIState.Success(
                            isLoading = false,
                            receta = receta,
                            progreso = progreso,
                            pasoActual = pasoActual,
                        )
                        delay(1000)
                    }
                } ?: run {
                    _estado.value = FlowRecetasUIState.Error("La receta ${uuidReceta} no existe")
                }
            } catch (e: Exception) {
                _estado.value = FlowRecetasUIState.Error("No se pudo cargar la receta")
                Log.d(TAG, e.stackTraceToString())
            }
        }


    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CookFlowApp)
                val repository = application.contenedor.recetasRepository
                val savedStateHandle = createSavedStateHandle()
                FlowViewModel(
                    repository = repository,
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }
}

