package com.example.cookat.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavBackStackEntry
import com.example.cookat.data.local.session.SessionManager
import com.example.cookat.repository.AuthRepository
import com.example.cookat.screens.auth.components.SmallTopAppBar
import com.example.cookat.viewmodels.auth.ResetPasswordViewModel
import kotlinx.coroutines.launch

@Composable
fun ResetPasswordScreen(
	backStackEntry: NavBackStackEntry,
	navController: NavController
) {
	val context = LocalContext.current
	val email = backStackEntry.arguments?.getString("email") ?: ""

	val viewModel: ResetPasswordViewModel = viewModel(
		factory = object : ViewModelProvider.Factory {
			override fun <T : ViewModel> create(modelClass: Class<T>): T {
				@Suppress("UNCHECKED_CAST")
				val sessionManager = SessionManager(context)
				val repo = AuthRepository(sessionManager, context)
				return ResetPasswordViewModel(repo) as T
			}
		}
	)

	val state by viewModel.uiState.collectAsState()
	val scope = rememberCoroutineScope()
	var showSuccessDialog by remember { mutableStateOf(false) }

	Scaffold(
		topBar = {
			SmallTopAppBar(
				title = { Text("Restablecer contraseña") },
				navigationIcon = {
					IconButton(onClick = { navController.popBackStack() }) {
						Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
					}
				}
			)
		}
	) { padding ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(padding)
				.padding(24.dp),
			verticalArrangement = Arrangement.Center
		) {
			// Mostrar email como texto fijo para no pedirlo de nuevo
			Text(text = "Email: $email", style = MaterialTheme.typography.bodyMedium)

			Spacer(modifier = Modifier.height(16.dp))

			OutlinedTextField(
				value = state.code,
				onValueChange = { viewModel.onCodeChange(it) },
				label = { Text("Código recibido") },
				modifier = Modifier.fillMaxWidth()
			)

			Spacer(modifier = Modifier.height(16.dp))

			OutlinedTextField(
				value = state.newPassword,
				onValueChange = { viewModel.onNewPasswordChange(it) },
				label = { Text("Nueva contraseña") },
				modifier = Modifier.fillMaxWidth(),
				visualTransformation = PasswordVisualTransformation()
			)

			Spacer(modifier = Modifier.height(16.dp))

			OutlinedTextField(
				value = state.confirmPassword,
				onValueChange = { viewModel.onConfirmPasswordChange(it) },
				label = { Text("Confirmar contraseña") },
				modifier = Modifier.fillMaxWidth(),
				visualTransformation = PasswordVisualTransformation()
			)

			Spacer(modifier = Modifier.height(16.dp))

			if (state.isLoading) {
				CircularProgressIndicator()
			} else {
				Button(
					onClick = {
						viewModel.submitReset(email) { success ->
							if (success) {
								showSuccessDialog = true
							}
						}
					},
					modifier = Modifier.fillMaxWidth()
				) {
					Text("Cambiar contraseña")
				}
			}

			if (state.errorMessage != null) {
				Text(text = "Error: ${state.errorMessage}", color = MaterialTheme.colorScheme.error)
			}
		}

		if (showSuccessDialog) {
			AlertDialog(
				onDismissRequest = { /* No se puede cerrar tocando afuera */ },
				title = { Text("Éxito") },
				text = { Text("La contraseña fue cambiada correctamente.") },
				confirmButton = {
					Button(onClick = {
						showSuccessDialog = false
						navController.navigate("home") {
							popUpTo("login") { inclusive = true }
						}
					}) {
						Text("Aceptar")
					}
				}
			)
		}
	}
}
