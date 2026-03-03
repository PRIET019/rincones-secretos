package es.fplumara.dam2.rinconessecretos.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import es.fplumara.dam2.rinconessecretos.data.local.AppDatabase
import es.fplumara.dam2.rinconessecretos.data.local.AppDatabaseProvider
import es.fplumara.dam2.rinconessecretos.navegation.Routes
import es.fplumara.dam2.rinconessecretos.ui.viewmodel.DetalleRinconViewModel

@Composable
fun RinconDetalleRoute(
    rinconId: Long,
    navController: NavController,
    viewModel: DetalleRinconViewModel = viewModel()
) {
    val context = LocalContext.current
    val dao = AppDatabaseProvider.getDatabase(context).rinconDao()

    // 🔥 CARGA EL RINCÓN CUANDO ENTRAS
    LaunchedEffect(rinconId) {
        viewModel.loadRincon(dao, rinconId)
    }

    val uiState by viewModel.state.collectAsStateWithLifecycle()

    RinconesDetalleContent(
        uiState = uiState,
        onBack = { navController.popBackStack() },
        onEdit =  {navController.navigate("rincon_edit/${uiState.id}")},
        onDelete = {
            viewModel.onDeleteRincon(dao) {
                navController.popBackStack()
            }
        },
        onCall = { viewModel.onCallPhone() },
        onOpenMaps = { viewModel.onOpenMaps() }
    )
}

