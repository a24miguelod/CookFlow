package local.a24miguelod.cookflow

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import local.a24miguelod.cookflow.ui.theme.CookFlowTheme

private const val TAG = "MainActivity"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        // TODO: esto no deberia ser asi
        // val viewModel: ListaRecetasViewModel by viewModels()
        // FirebaseApp.initializeApp(this)

        // TODO: poner estas cosas en un panel de admin
        // cargarIngredientes()
        //cargarRecetas(10)

        val splashScreen = installSplashScreen()


        setTheme(R.style.Theme_CookFlow) // si no, se mantiene el icon del splash
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            CookFlowTheme (
                dynamicColor = false
            ){
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CookFlowNavGraph()
                }
            }
        }

    }
}