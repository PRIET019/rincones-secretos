package es.fplumara.dam2.rinconessecretos.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.fplumara.dam2.rinconessecretos.data.location.getCurrentLatLon
import es.fplumara.dam2.rinconessecretos.ui.screen.CrearRinconUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CrearRinconViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CrearRinconUIState())
    val uiState: StateFlow<CrearRinconUIState> = _uiState.asStateFlow()

    fun onNombreChange(value: String) {
        _uiState.update { it.copy(nombre = value) }
    }

    fun onCategoriaChange(value: String) {
        _uiState.update { it.copy(categoria = value) }
    }

    fun onTelefonoChange(value: String) {
        _uiState.update { it.copy(telefono = value) }
    }

    fun onDescripcionChange(value: String) {
        _uiState.update { it.copy(descripcion = value) }
    }

    fun onPhotoTaken(uri: String) {
        _uiState.update { it.copy(photoUri = uri) }
    }


    fun onCreateDraft() {
        // guardar en Room como borrador
    }

    fun onPermissionDenied() {
        _uiState.update { it.copy(isLoading = false, message = "Permiso de ubicación denegado.") }
    }

    fun fetchLocation(context: Context) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = "")}
            val latLon = getCurrentLatLon(context)
            _uiState.update {
                if (latLon != null) {
                    it.copy(isLoading = false, latitude = latLon.first, longitude = latLon.second, message = "")
                } else {
                    it.copy(isLoading = false, message = "No se pudo obtener la ubicación.")
                }
            }
        }
    }


}
