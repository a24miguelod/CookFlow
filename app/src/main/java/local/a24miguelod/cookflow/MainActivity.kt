package local.a24miguelod.cookflow

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.FirebaseApp
import local.a24miguelod.cookflow.core.navigation.NavigationWrapper
import local.a24miguelod.cookflow.ui.RecetasViewModel
import local.a24miguelod.cookflow.ui.theme.MaterialCookFlowTheme
import local.a24miguelod.cookflow.utils.cargarIngredientes
import local.a24miguelod.cookflow.utils.cargarRecetas

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        // TODO: esto no deberia ser asi
        val viewModel: RecetasViewModel by viewModels()
        FirebaseApp.initializeApp(this)

        // TODO: poner estas cosas en un panel de admin
        // cargarIngredientes()
        //cargarRecetas(10)

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            viewModel.recetas.value.isEmpty()
        }


        setTheme(R.style.Theme_CookFlow) // si no, se mantiene el icon del splash
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            MaterialCookFlowTheme {
                NavigationWrapper(viewModel)
            }
        }

    }
}