package es.fplumara.dam2.rinconessecretos.ui.screen

import es.fplumara.dam2.rinconessecretos.data.local.Rincon



data class RinconUIState(
    val rincon: List<Rincon> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null,
    val rincones: List<Rincon> = emptyList()
)

data class CrearRinconUIState(
    val id: Long? = null,
    val isLoading: Boolean = false,
    val nombre: String = "",
    val categoria: String = "",
    val telefono: String = "",
    val descripcion: String = "",
    val latitude: Double? = null,
    val longitude: Double? = null,
    val photoUri: String? = null,
    val message: String = ""

)

data class EditarRinconUIState(
    val id: Long? = null,
    val nombre: String = "",
    val categoria: String = "",
    val telefono: String = "",
    val descripcion: String = "",
    val latitude: Double? = null,
    val longitude: Double? = null,
    val photoUri: String? = null,
)

data class DetalleRinconUIState(
    val id: Long = 0,
    val nombre: String = "",
    val categoria: String = "",
    val telefono: String = "",
    val descripcion: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val photoUri: String = "",
    val isLoading: Boolean = true
)

data class LocationUiState(
    val id: Long,
    val isLoading: Boolean = false,
    val nombre: String = "",
    val categoria: String = "",
    val telefono: String = "",
    val descripcion: String = "",
    val latitude: Double? = null,
    val longitude: Double? = null,
    val message: String = ""
)



