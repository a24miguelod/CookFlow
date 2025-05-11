package local.a24miguelod.cookflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import local.a24miguelod.cookflow.ui.theme.CookFlowTheme

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
            CookFlowTheme {
                CookFlowNavGraph()
            }
        }

    }
}