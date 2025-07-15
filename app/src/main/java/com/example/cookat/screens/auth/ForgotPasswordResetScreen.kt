package com.example.cookat.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavBackStackEntry
import com.example.cookat.data.local.session.SessionManager
import com.example.cookat.network.BackendClient
import com.example.cookat.repository.AuthRepository
import com.example.cookat.viewmodels.auth.ResetPasswordViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordResetScreen(
	backStackEntry: NavBackStackEntry,
	navController: NavController
) {
	val context = LocalContext.current
	val email = backStackEntry.arguments?.getString("email") ?: ""

	val viewModel: ResetPasswordViewModel = viewModel {
		ResetPasswordViewModel(
			authRepository = AuthRepository(SessionManager(context), context)
		)
	}

	val state by viewModel.uiState.collectAsState()

	// Inicializamos el email una sola vez
	LaunchedEffect(email) {
		viewModel.setEmail(email)
	}
	var showDialog by remember { mutableStateOf(false) }

	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text("Cambiar contraseña") },
				navigationIcon = {
					IconButton(onClick = { navController.popBackStack() }) {
						Icon(
							imageVector = Icons.AutoMirrored.Filled.ArrowBack,
							contentDescription = "Volver"
						)
					}
				}
			)
		}
	) { padding ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(padding)
				.padding(horizontal = 24.dp),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			OutlinedTextField(
				value = state.code,
				onValueChange = viewModel::onCodeChange,
				label = { Text("Código de verificación") },
				keyboardOptions = KeyboardOptions.Default.copy(
					imeAction = ImeAction.Next,
					keyboardType = KeyboardType.Number
				),
				modifier = Modifier.fillMaxWidth()
			)

			Spacer(modifier = Modifier.height(12.dp))

			OutlinedTextField(
				value = state.newPassword,
				onValueChange = viewModel::onNewPasswordChange,
				label = { Text("Nueva contraseña") },
				visualTransformation = PasswordVisualTransformation(),
				keyboardOptions = KeyboardOptions.Default.copy(
					imeAction = ImeAction.Next,
					keyboardType = KeyboardType.Password
				),
				modifier = Modifier.fillMaxWidth()
			)

			Spacer(modifier = Modifier.height(12.dp))

			OutlinedTextField(
				value = state.confirmPassword,
				onValueChange = viewModel::onConfirmPasswordChange,
				label = { Text("Confirmar contraseña") },
				visualTransformation = PasswordVisualTransformation(),
				keyboardOptions = KeyboardOptions.Default.copy(
					imeAction = ImeAction.Done,
					keyboardType = KeyboardType.Password
				),
				modifier = Modifier.fillMaxWidth()
			)

			Spacer(modifier = Modifier.height(16.dp))

			if (state.isLoading) {
				CircularProgressIndicator()
			} else {
				Button(
					onClick = {
						viewModel.submit {
							showDialog = true
						}
					},
					modifier = Modifier.fillMaxWidth()
				) {
					Text("Cambiar contraseña")
				}
			}

			state.errorMessage?.let {
				Spacer(modifier = Modifier.height(8.dp))
				Text(it, color = MaterialTheme.colorScheme.error)
			}
		}
	}

	// Modal de éxito
	if (showDialog) {
		AlertDialog(
			onDismissRequest = {
				showDialog = false
				navController.navigate("login") {
					popUpTo("login") { inclusive = true }
				}
			},
			confirmButton = {
				TextButton(onClick = {
					showDialog = false
					navController.navigate("login") {
						popUpTo("login") { inclusive = true }
					}
				}) {
					Text("Aceptar")
				}
			},
			title = { Text("Contraseña cambiada") },
			text = { Text("Tu contraseña fue actualizada con éxito.") }
		)
	}
}
