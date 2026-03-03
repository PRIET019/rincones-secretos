package es.fplumara.dam2.rinconessecretos.data.local

data class Rincon(
    val id: Long,
    val nombre: String,
    val descripcion: String,
    val categoria: String,
    val telefono: String,
    val photoUri: String?,
    val latitude: Double?,
    val longitude: Double?

)