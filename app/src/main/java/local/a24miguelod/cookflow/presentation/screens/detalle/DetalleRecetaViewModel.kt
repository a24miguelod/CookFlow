package local.a24miguelod.cookflow.presentation.screens.detalle

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
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import local.a24miguelod.cookflow.presentation.navigation.CockFlowDestinationsArgs
import local.a24miguelod.cookflow.CookFlowApp
import local.a24miguelod.cookflow.data.repository.CacheRepository
import local.a24miguelod.cookflow.data.repository.RecetasRepository
import local.a24miguelod.cookflow.domain.model.Ingrediente
import local.a24miguelod.cookflow.domain.model.Receta

private const val TAG = "DetalleRecetaViewModel"

sealed class DetalleRecetaUIState {
    data object Loading : DetalleRecetaUIState()
    data class Success(
        val receta: Receta,
        val isLoading: Boolean = false
    ) : DetalleRecetaUIState()

    data class Error(val message: String) : DetalleRecetaUIState()
}

class DetalleRecetaViewModel(
    private val repository: RecetasRepository,
    private val cacheRepository: CacheRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val recetaUuid: String = savedStateHandle[CockFlowDestinationsArgs.RECETA_ID]!!

    private val _estado = MutableStateFlow<DetalleRecetaUIState>(DetalleRecetaUIState.Loading)
    val estado: StateFlow<DetalleRecetaUIState> = _estado

    init {
        Log.d(TAG, "savedStateHandle $recetaUuid")
        Log.d(TAG, "Inicializando DetalleRecetaViewModel")
        Log.d(TAG, "SavedStateHandle keys: ${savedStateHandle.keys().joinToString()}")
        getReceta(recetaUuid)
    }

    private fun getReceta(uuidReceta: String) {
        _estado.value = DetalleRecetaUIState.Loading

        viewModelScope.launch {
            try {
                /* Se refatoriza aparte para poder refrescarlo facilmente cuando
                   cambia un ingrediente (por anadirlo o eliminarlo de la despensa/lista compra
                 */
                updateUI()
            } catch (e: Exception) {
                _estado.value = DetalleRecetaUIState.Error("No se pudo cargar la receta")
                Log.d(TAG, e.stackTraceToString())
            }
        }
    }

    fun toggleDespensa(ingrediente: Ingrediente) {
        viewModelScope.launch {
            val ing = cacheRepository.getIngredienteCacheado(ingrediente)
            cacheRepository.setIngredienteDisponible(ing.ingredienteId, !ing.enDespensa)
            Log.d(TAG, "toggleDespensa $ingrediente")
            updateUI()
        }
    }

    fun anadirAListaCompra(ingrediente: Ingrediente) {
        viewModelScope.launch {
            val ing = cacheRepository.getIngredienteCacheado(ingrediente)
            cacheRepository.setIngredienteEnListaCompra(ing.ingredienteId, !ing.enListaCompra)
            Log.d(TAG, "toggleListaCompra $ingrediente")
            updateUI()
        }
    }


    fun eliminarDeListaCompra(ingrediente: Ingrediente)  {
        Log.d(TAG, "eliminarDeDespensa $ingrediente")
    }

    fun eliminarDeDespensa(ingrediente: Ingrediente)  {
        Log.d(TAG, "eliminarDeDespensa $ingrediente")
    }


    private suspend fun updateUI() {
        repository.getReceta(recetaUuid)?.let { receta ->
            // Obtiene la disponibilidad dede Room
            receta.ingredientes.forEach() {
                val ingredienteEnCache =
                    cacheRepository.getIngredienteCacheado(it.ingrediente)
                it.ingrediente.enDespensa = ingredienteEnCache.enDespensa
                it.ingrediente.enListaCompra = ingredienteEnCache.enListaCompra
            }
            _estado.value = DetalleRecetaUIState.Success(receta, false)



        } ?: run {
            _estado.value = DetalleRecetaUIState.Error("La receta ${recetaUuid} no existe")
        }
    }

}

