package es.fplumara.dam2.rinconessecretos.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarRinconContent(
    uiState: EditarRinconUIState,
    onNombreChange: (String) -> Unit,
    onCategoriaChange: (String) -> Unit,
    onTelefonoChange: (String) -> Unit,
    onDescripcionChange: (String) -> Unit,
    onUseLocation: () -> Unit,
    onTakePhoto: () -> Unit,
    onSaveChanges: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Editar rincón") })
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

            Text(
                text = "Ubicación: Lat: ${uiState.latitude}  Lon: ${uiState.longitude}",
                style = MaterialTheme.typography.bodySmall
            )

            Button(onClick = onUseLocation) {
                Text("Usar ubicación actual")
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onTakePhoto,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.CameraAlt, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Hacer foto")
                }

                OutlinedButton(
                    onClick = { /* ver foto */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Image, contentDescription = null)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onSaveChanges,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar cambios")
            }
        }
    }
}
