package es.fplumara.dam2.rinconessecretos.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rincones")
data class RinconEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val nombre: String,
    val descripcion: String,
    val categoria: String,
    val telefono: String,
    val photoUri: String?,
    val latitude: Double?,
    val longitude: Double?
)
