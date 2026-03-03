package es.fplumara.dam2.rinconessecretos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import es.fplumara.dam2.rinconessecretos.navegation.AppNavHost
import es.fplumara.dam2.rinconessecretos.ui.theme.RinconesSecretosTheme
import es.fplumara.dam2.rinconessecretos.ui.viewmodel.DetalleRinconViewModel
import es.fplumara.dam2.rinconessecretos.ui.viewmodel.RinconViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val rinconViewModel: RinconViewModel = viewModel()
    val detalleViewModel: DetalleRinconViewModel = viewModel()
    RinconesSecretosTheme {
        AppNavHost(navController = navController, rinconViewModel = rinconViewModel, detalleViewModel= detalleViewModel)
    }
}