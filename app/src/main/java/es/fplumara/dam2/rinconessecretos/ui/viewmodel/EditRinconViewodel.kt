package es.fplumara.dam2.rinconessecretos.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.fplumara.dam2.rinconessecretos.data.local.RinconDao
import es.fplumara.dam2.rinconessecretos.data.local.RinconEntity
import es.fplumara.dam2.rinconessecretos.ui.screen.EditarRinconUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditarRinconViewModel(
    private val dao: RinconDao
) : ViewModel() {

    private val _state = MutableStateFlow(EditarRinconUIState())
    val state: StateFlow<EditarRinconUIState> = _state


    fun loadRincon(id: Long) {
        viewModelScope.launch {
            val rincon = dao.getById(id)
            _state.value = state.value.copy(
                id= rincon?.id,
                nombre = rincon?.nombre ?: "",
                descripcion = rincon?.descripcion ?: "",
                categoria = rincon?.categoria ?: "",
                telefono = rincon?.telefono ?: "",
                latitude = rincon?.latitude,
                longitude = rincon?.longitude,
                photoUri = rincon?.photoUri
            )
        }
    }




    fun onNombreChange(value: String) {
        _state.update { it.copy(nombre = value) }
    }

    fun onCategoriaChange(value: String) {
        _state.update { it.copy(categoria = value) }
    }

    fun onTelefonoChange(value: String) {
        _state.update { it.copy(telefono = value) }
    }



    fun onUseCurrentLocation() {
        // lógica ubicación
    }

    fun onTakePhoto() {
        // lógica cámara
    }

    fun onSaveChanges() {
        viewModelScope.launch {

            val current = state.value

            val rinconActualizado = RinconEntity(
                id = current.id?: return@launch,
                nombre = current.nombre,
                descripcion = current.descripcion,
                categoria = current.categoria,
                telefono = current.telefono,
                latitude = current.latitude,
                longitude = current.longitude,
                photoUri = current.photoUri
            )

            dao.update(rinconActualizado)
        }
    }

}
