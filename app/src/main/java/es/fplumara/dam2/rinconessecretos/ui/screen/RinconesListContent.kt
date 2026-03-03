package es.fplumara.dam2.rinconessecretos.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import es.fplumara.dam2.rinconessecretos.data.local.Rincon

@Composable
fun RinconesListContent(
    rincones: List<Rincon>,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onCreateRincon: () -> Unit,
    onRinconClick: (Long) -> Unit,
    onPhotoClick: (Long) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateRincon, modifier = Modifier.fillMaxWidth()) {
                Text("+ Crear Borrador")
            }
        }
    ) { padding ->

        Column(modifier = Modifier.padding(padding)) {

            TextField(
                value = searchQuery,
                onValueChange = onSearchChange,
                placeholder = { Text("Buscar") }
            )

            LazyColumn {
                items(rincones) { rincon ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                rincon.id?.let { id ->
                                    onRinconClick(id)
                                }
                            }


                    ) {
                        Column(Modifier.padding(12.dp)) {

                            Text(rincon.nombre, style = MaterialTheme.typography.titleMedium)
                            Text(rincon.categoria)
                            Text("Tel: ${rincon.telefono}")

                            if (rincon.descripcion.isNotBlank()) {
                                Text(rincon.descripcion)
                            }

                            if (rincon.latitude != null && rincon.longitude != null) {
                                Text("📍 ${rincon.latitude}, ${rincon.longitude}")
                            }

                            if (rincon.photoUri != null) {
                                AsyncImage(model = rincon.photoUri, contentDescription = null)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RinconItem(
    rincon: Rincon,
    onClick: () -> Unit,
    onPhotoClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(text = rincon.nombre)
        Text(text = rincon.categoria ?: "")

        Text(
            text = "Añadir foto",
            modifier = Modifier.clickable { onPhotoClick() }
        )
    }
}
