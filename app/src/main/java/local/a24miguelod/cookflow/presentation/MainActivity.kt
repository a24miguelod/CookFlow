package local.a24miguelod.cookflow.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import local.a24miguelod.cookflow.CookFlowNavGraph
import local.a24miguelod.cookflow.R
import local.a24miguelod.cookflow.ui.theme.CookFlowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        setTheme(R.style.Theme_CookFlow) // si no, se mantiene el icon del splash
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CookFlowTheme(
                dynamicColor = true
            ) {
                CookFlowNavGraph()
            }
        }

    }
}