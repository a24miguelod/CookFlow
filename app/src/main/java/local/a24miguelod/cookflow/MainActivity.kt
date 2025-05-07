package local.a24miguelod.cookflow

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
import local.a24miguelod.cookflow.ui.theme.CookFlowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        // TODO: esto no deberia ser asi
        val viewModel: RecetasViewModel by viewModels()
        FirebaseApp.initializeApp(this)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CookFlowTheme {
                NavigationWrapper(viewModel)
            }
        }
        installSplashScreen()
    }
}