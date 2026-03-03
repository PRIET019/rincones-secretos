package es.fplumara.dam2.rinconessecretos.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.fplumara.dam2.rinconessecretos.data.local.RinconDao
import es.fplumara.dam2.rinconessecretos.ui.screen.DetalleRinconUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
class DetalleRinconViewModel() : ViewModel() {


    private val _state = MutableStateFlow(DetalleRinconUIState())
    val state: StateFlow<DetalleRinconUIState> = _state

    fun loadRincon(dao: RinconDao, rinconId: Long) {
        viewModelScope.launch {
            val rincon = dao.getById(rinconId)

            _state.update {
                if (rincon != null) {
                    it.copy(
                        id = rincon.id,
                        nombre = rincon.nombre ?: "",
                        categoria = rincon.categoria ?: "",
                        telefono = rincon.telefono ?: "",
                        descripcion = rincon.descripcion ?: "",
                        latitude = rincon.latitude ?: 0.0,
                        longitude = rincon.longitude ?: 0.0,
                        photoUri = rincon.photoUri ?: "",
                        isLoading = false
                    )

                } else {
                    it.copy(isLoading = false)
                }
            }
        }
    }

    fun onDeleteRincon(dao: RinconDao, onFinish: () -> Unit) {
        val id = state.value.id
        viewModelScope.launch {
            dao.deleteById(id)
            onFinish()
        }
    }

    fun onPhotoTaken(uri: String) {
        _state.update { it.copy(photoUri = uri) }
    }



    fun onCallPhone() {}
    fun onOpenMaps() {}

}

