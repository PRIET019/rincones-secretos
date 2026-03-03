package es.fplumara.dam2.rinconessecretos.ui.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import es.fplumara.dam2.rinconessecretos.ui.viewmodel.CrearRinconViewModel
import android.Manifest
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import es.fplumara.dam2.rinconessecretos.data.camera.createImageUri
import es.fplumara.dam2.rinconessecretos.data.local.Rincon
import es.fplumara.dam2.rinconessecretos.ui.viewmodel.RinconViewModel


@Composable
fun CrearRinconesRoute(
    navController: NavController,
    viewModel: CrearRinconViewModel = viewModel(),
    rinconVm: RinconViewModel

) {

    val context = LocalContext.current
   val pendingUriState = remember { mutableStateOf<Uri?>(null)}
    val photoUriState = remember {mutableStateOf<Uri?>(null)}
    val messageState = remember { mutableStateOf<String?>(null) }


    val takePicture = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { ok ->
        if (ok) {
            val uri = pendingUriState.value
            photoUriState.value = uri

            // 🔥 ESTA ES LA LÍNEA QUE FALTABA
            uri?.let { viewModel.onPhotoTaken(it.toString()) }

            messageState.value = null
        } else {
            messageState.value = "No se hizo la foto."
        }
    }


    val requestCameraPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            val uri = createImageUri(context)
            pendingUriState.value = uri
            takePicture.launch(uri)
        } else {
            messageState.value = "Permiso de cámara denegado."
        }
    }


    val uiState by viewModel.uiState.collectAsState()
    val requestPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) viewModel.fetchLocation(context)
        else viewModel.onPermissionDenied()
    }


    CrearRinconesContent(
        photoUri = photoUriState.value,
        uiState = uiState,
        onNombreChange = viewModel::onNombreChange,
        onCategoriaChange = viewModel::onCategoriaChange,
        onTelefonoChange = viewModel::onTelefonoChange,
        onDescripcionChange = viewModel::onDescripcionChange,
        onUseLocation = {requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)},
        onTakePhoto = { requestCameraPermission.launch(Manifest.permission.CAMERA) },
        onCreateDraft = {

            val nuevo = Rincon(
                id = 0,
                nombre = uiState.nombre,
                categoria = uiState.categoria,
                telefono = uiState.telefono,
                descripcion = uiState.descripcion,
                latitude = uiState.latitude,
                longitude = uiState.longitude,
                photoUri = uiState.photoUri
            )

            rinconVm.addRincon(nuevo)   // 🔥 GUARDA EN ROOM
            navController.popBackStack()
        }

    )
}


