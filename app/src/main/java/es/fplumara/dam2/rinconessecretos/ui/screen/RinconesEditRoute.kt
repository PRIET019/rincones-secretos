package es.fplumara.dam2.rinconessecretos.ui.screen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import es.fplumara.dam2.rinconessecretos.data.local.AppDatabase
import es.fplumara.dam2.rinconessecretos.ui.viewmodel.EditarRinconViewModel


@Composable
fun RinconesEditRoute(
    rinconId: Long,
    navController: NavController,
) {

    val context = LocalContext.current

    val dao = AppDatabase.getDatabase(context).rinconDao()

    val viewModel = remember {
        EditarRinconViewModel(dao)
    }



    LaunchedEffect(rinconId) {
        Log.d("EDIT_DEBUG", "ID recibido: $rinconId")
        viewModel.loadRincon(rinconId)
    }



    val uiState by viewModel.state.collectAsStateWithLifecycle()

    EditarRinconContent(
        uiState = uiState,
        onNombreChange = viewModel::onNombreChange,
        onCategoriaChange = viewModel::onCategoriaChange,
        onTelefonoChange = viewModel::onTelefonoChange,
        onDescripcionChange = viewModel::onDescripcionChange,
        onUseLocation = viewModel::onUseCurrentLocation,
        onTakePhoto = viewModel::onTakePhoto,
        onSaveChanges = {
            viewModel.onSaveChanges()
            navController.popBackStack()
        }
    )
}
