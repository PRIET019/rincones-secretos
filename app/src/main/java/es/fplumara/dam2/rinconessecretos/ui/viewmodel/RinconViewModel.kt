package es.fplumara.dam2.rinconessecretos.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import es.fplumara.dam2.rinconessecretos.data.local.AppDatabase
import es.fplumara.dam2.rinconessecretos.data.local.Rincon
import es.fplumara.dam2.rinconessecretos.data.local.RinconEntity
import es.fplumara.dam2.rinconessecretos.ui.screen.RinconUIState
import es.fplumara.dam2.rinconessecretos.ui.screen.RinconesListRoute
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class RinconViewModel(app: Application) : AndroidViewModel(app) {

    private val _uiState = MutableStateFlow<List<Rincon>>(emptyList())
    val rincones: StateFlow<List<Rincon>> = _uiState



    private val db = Room.databaseBuilder(
        app, AppDatabase::class.java, "rincones_db"
    ).fallbackToDestructiveMigration().build()


    private val dao = db.rinconDao()



    val searchQuery = MutableStateFlow("")

    fun onSearchChange(text: String) {
        searchQuery.value = text
    }

    val state: StateFlow<RinconUIState> =
        dao.getAll()
            .map{ lista: List<RinconEntity> ->
                RinconUIState(
                    rincones = lista.map { Rincon(it.id, it.nombre,
                        it.descripcion, it.categoria,
                        it.telefono, it.photoUri,
                        it.latitude, it.longitude) }
                )
            }.stateIn(viewModelScope, SharingStarted.Lazily, RinconUIState())


    fun addRincon(rincon: Rincon) {
        viewModelScope.launch {
            val newId = dao.insert(
                RinconEntity(
                    id = 0,
                    nombre = rincon.nombre,
                    descripcion = rincon.descripcion,
                    categoria = rincon.categoria,
                    telefono = rincon.telefono,
                    photoUri = rincon.photoUri,
                    latitude = rincon.latitude,
                    longitude = rincon.longitude
                )
            )
            Log.d("DB", "Rincón guardado con ID = $newId")
        }
    }




    fun onAddPhoto(rinconId: Long) {
    }



}
