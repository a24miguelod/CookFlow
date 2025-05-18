package local.a24miguelod.cookflow.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import local.a24miguelod.cookflow.presentation.navigation.CookFlowNavGraph
import local.a24miguelod.cookflow.R
import local.a24miguelod.cookflow.presentation.theme.CookFlowTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        setTheme(R.style.Theme_CookFlow) // si no, se mantiene el icon del splash
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CookFlowTheme {
                val navController = rememberNavController()
                CookFlowNavGraph(navController)
            }
        }
    }
}