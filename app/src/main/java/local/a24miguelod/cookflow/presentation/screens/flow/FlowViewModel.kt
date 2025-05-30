package local.a24miguelod.cookflow.presentation.screens.flow

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import local.a24miguelod.cookflow.presentation.navigation.CookFlowDestinationsArgs
import local.a24miguelod.cookflow.data.repository.RecetasRepository
import local.a24miguelod.cookflow.domain.model.Receta


private const val TAG = "FlowViewModel"

sealed class FlowRecetasUIState {
    data object Loading : FlowRecetasUIState()
    data class Success(
        val receta: Receta,
        val isLoading: Boolean = false,
        val pasoActual: Int = 0
    ) : FlowRecetasUIState()

    data class Error(val message: String) : FlowRecetasUIState()
}

data class Cronometro (
    var duracion: Float = 0f,
    var startTime: Long = 0,
    var elapsed: Long = 0,
    var progreso: Float = 0f,
    var running: Boolean = false
)

class FlowViewModel(
    private val repository: RecetasRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _estado = MutableStateFlow<FlowRecetasUIState>(FlowRecetasUIState.Loading)
    val estado: StateFlow<FlowRecetasUIState> = _estado

    private val _cronometro = MutableStateFlow<Cronometro>(Cronometro())
    val cronometro:StateFlow<Cronometro> = _cronometro

    private val recetaUuid: String = savedStateHandle[CookFlowDestinationsArgs.RECETA_ID]!!

    init {
        getReceta(recetaUuid)
    }

    private fun getReceta(uuidReceta: String) {
        _estado.value = FlowRecetasUIState.Loading

        viewModelScope.launch {
            try {
                repository.getReceta(uuidReceta)?.let { receta ->

                    _estado.value = FlowRecetasUIState.Success(
                        isLoading = false,
                        receta = receta,
                        pasoActual = 0,
                    )

                    var startTime = System.currentTimeMillis()
                    _cronometro.value = Cronometro(
                        duracion = receta.pasos[0].duracion,
                        startTime = startTime,
                        elapsed = 0,
                        running = true
                    )

                    while (_cronometro.value.running) {
                        var elapsed = System.currentTimeMillis() - _cronometro.value.startTime
                        var progreso = (elapsed.toFloat() / (receta.pasos[0].duracion*60000f)).coerceIn(0f, 1f)
                        _cronometro.value = _cronometro.value.copy(
                            elapsed = elapsed,
                            progreso = progreso
                        )
                        if (progreso >= 1f) mostrarPasoSiguiente()
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

    fun mostrarPasoSiguiente() {
        var estadoActual = _estado.value
        if (estadoActual is FlowRecetasUIState.Success) {
            val nuevoPaso = estadoActual.pasoActual + 1
            estadoActual = estadoActual.copy(
                pasoActual = nuevoPaso
            )
            _estado.value = estadoActual

            if (nuevoPaso < estadoActual.receta.pasos.size) {
                _cronometro.value = _cronometro.value.copy (
                    duracion = estadoActual.receta.pasos[nuevoPaso].duracion,
                    startTime = System.currentTimeMillis(),
                    elapsed = 0,
                    progreso = 0f,
                    running = true,
                )
            } else {
                _cronometro.value = _cronometro.value.copy (
                    duracion = 0f,
                    startTime = System.currentTimeMillis(),
                    elapsed = 0,
                    running = false,
                )
            }
        }
    }

    fun mostrarPasoAnterior() {
        var estadoActual = _estado.value
        if (estadoActual is FlowRecetasUIState.Success) {
            val nuevoPaso = estadoActual.pasoActual - 1
            if (nuevoPaso < estadoActual.receta.pasos.size) {
                estadoActual = estadoActual.copy(
                    pasoActual = nuevoPaso
                )
                _estado.value = estadoActual
                _cronometro.value = _cronometro.value.copy (
                    running = false
                )
            }
        }
    }

}

