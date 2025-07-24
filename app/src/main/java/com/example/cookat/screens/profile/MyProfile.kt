package com.example.cookat.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cookat.viewmodels.profile.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfile(
	viewModel: ProfileViewModel,
	onNavigateBack: () -> Unit,
	onNavigateTo: () -> Unit,
	onLogout: () -> Unit
) {
	val uiState by viewModel.uiState.collectAsState()

	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text("Perfil") },
				navigationIcon = {
					IconButton(onClick = onNavigateBack) {
						Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
					}
				}
			)
		}
	) { padding ->
		Column(
			modifier = Modifier
				.padding(padding)
				.fillMaxSize()
				.padding(16.dp),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			OutlinedTextField(
				value = uiState.editedEmail,
				onValueChange = viewModel::onEmailChange,
				label = { Text("Email") }
			)

			Spacer(modifier = Modifier.height(12.dp))

			OutlinedTextField(
				value = uiState.editedUsername,
				onValueChange = viewModel::onUsernameChange,
				label = { Text("Username") }
			)

			Spacer(modifier = Modifier.height(24.dp))

			if (uiState.isLoading) {
				CircularProgressIndicator()
			} else {
				Button(
					onClick = viewModel::showDialog,
					enabled = uiState.isChanged
				) {
					Text("Actualizar Perfil")
				}
			}

			Spacer(modifier = Modifier.height(24.dp))

			
			Button(onClick = onLogout) {
				Text("Cerrar sesión")
			}

			uiState.error?.let {
				Spacer(modifier = Modifier.height(12.dp))
				Text("Error: $it", color = MaterialTheme.colorScheme.error)
			}
		}
	}

	if (uiState.showConfirmationDialog) {
		AlertDialog(
			onDismissRequest = viewModel::dismissDialog,
			title = { Text("Confirmacion de Cambios") },
			text = { Text("¿Estás seguro que quieres actualizar tu perfil? Este cambio no puede deshacerse.") },
			confirmButton = {
				TextButton(onClick = { viewModel.confirmUpdate(onNavigateTo) }) {
					Text("Sí")
				}
			},
			dismissButton = {
				TextButton(onClick = viewModel::dismissDialog) {
					Text("Cancelar")
				}
			}
		)
	}
}