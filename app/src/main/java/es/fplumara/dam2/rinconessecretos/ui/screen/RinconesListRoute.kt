package es.fplumara.dam2.rinconessecretos.ui.screen



import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import es.fplumara.dam2.rinconessecretos.navegation.Routes
import es.fplumara.dam2.rinconessecretos.ui.viewmodel.RinconViewModel


@Composable
fun RinconesListRoute(
    navController: NavController,
    vm: RinconViewModel,
) {

    val context = LocalContext.current
    val uiState by vm.state.collectAsState()
    val searchQuery by vm.searchQuery.collectAsStateWithLifecycle()







    RinconesListContent(
        rincones = uiState.rincones,
        searchQuery = searchQuery,
        onSearchChange = vm::onSearchChange,
        onCreateRincon = {
            navController.navigate(Routes.CREAR)
        },

        onRinconClick = { id ->
            navController.navigate("detalle/$id")
        },


                onPhotoClick = { rinconId ->
            vm.onAddPhoto(rinconId)
        },

    )
}