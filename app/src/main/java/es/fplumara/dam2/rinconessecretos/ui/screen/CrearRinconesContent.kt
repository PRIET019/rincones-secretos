package es.fplumara.dam2.rinconessecretos.ui.screen

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearRinconesContent(
    photoUri: Uri?,
    uiState: CrearRinconUIState,
    onNombreChange: (String) -> Unit,
    onCategoriaChange: (String) -> Unit,
    onTelefonoChange: (String) -> Unit,
    onDescripcionChange: (String) -> Unit,
    onUseLocation: () -> Unit,
    onTakePhoto: () -> Unit,
    onCreateDraft: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Crear rincón") })
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = onNombreChange,
                label = { Text("Nombre*") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.categoria,
                onValueChange = onCategoriaChange,
                label = { Text("Categoría*") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.telefono,
                onValueChange = onTelefonoChange,
                label = { Text("Teléfono*") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            OutlinedTextField(
                value = uiState.descripcion,
                onValueChange = onDescripcionChange,
                label = { Text("Descripción") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )



            Button ( onClick = onUseLocation,
                   enabled = !uiState.isLoading
            ) {
                Text(if (uiState.isLoading) "Obteniendo..." else "Obtener ubicaicon actual")
            }

            if (uiState.latitude != null && uiState.longitude != null){
                Text("Lat: ${uiState.latitude}")
                Text("Lon: ${uiState.longitude}")
            } else {
                Text("Lan/Lon: -")
            }


            OutlinedButton(
                onClick = onTakePhoto,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.CameraAlt, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Hacer foto")
            }

            if (photoUri != null) {
                AsyncImage(model = photoUri, contentDescription = "Foto tomada")
            } else {
                Text("No hay foto todavía.")
            }



            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onCreateDraft,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("+ Crear rincon")
            }
        }
    }
}
