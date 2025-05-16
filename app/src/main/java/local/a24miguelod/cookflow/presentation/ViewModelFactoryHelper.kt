package local.a24miguelod.cookflow.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// https://www.youtube.com/watch?v=eX-y0IEHJjM&t=690s
fun <VM:ViewModel> viewModelFactory(initializer: () -> VM):ViewModelProvider.Factory {
    return object:ViewModelProvider.Factory {
        override fun <T: ViewModel> create(modelClass:Class<T>): T {
            return initializer() as T
        }
    }
}