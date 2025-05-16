package local.a24miguelod.cookflow.presentation

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner

private const val TAG = "ViewModelFactoryHelper"

// https://www.youtube.com/watch?v=eX-y0IEHJjM&t=690s
fun <VM:ViewModel> viewModelFactory(initializer: () -> VM):ViewModelProvider.Factory {
    return object:ViewModelProvider.Factory {
        override fun <T: ViewModel> create(modelClass:Class<T>): T {
            Log.d(TAG,"Estoy en la fatory")
            return initializer() as T
        }
    }
}

// Caso mas generico si necesito el savedStateHandle
fun <VM : ViewModel> savedStateViewModelFactory(
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null,
    initializer: (SavedStateHandle) -> VM
): AbstractSavedStateViewModelFactory {
    return object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
        override fun <T : ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {

            Log.d("ViewModelFactory", "➡️ Creando ViewModel: ${modelClass.simpleName}")
            Log.d("ViewModelFactory", "➡️ SavedStateHandle creado: $handle")

            // Mostrar claves y valores
            if (handle.keys().isEmpty()) {
                Log.w("ViewModelFactory", "⚠️ SavedStateHandle sin argumentos")
            } else {
                handle.keys().forEach { key ->
                    val value = handle.get<Any?>(key)
                    Log.d("ViewModelFactory", "🗝️ key: '$key' -> value: '$value'")
                }
            }

            Log.d(TAG, "Factory de AbastractSavedState")
            return initializer(handle) as T
        }
    }
}