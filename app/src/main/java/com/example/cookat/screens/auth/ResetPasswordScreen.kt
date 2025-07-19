package com.example.cookat.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavBackStackEntry
import com.example.cookat.data.local.session.SessionManager
import com.example.cookat.repository.AuthRepository
import com.example.cookat.viewmodels.auth.ResetPasswordViewModel

@OptIn(ExperimentalMaterial3Api::class)
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
				val repo = AuthRepository(SessionManager(context), context)
				return ResetPasswordViewModel(repo) as T
			}
		}
	)

	val state by viewModel.uiState.collectAsState()
	var showSuccessDialog by remember { mutableStateOf(false) }

	// Validaciones locales
	val isCodeValid by remember(state.code) {
		derivedStateOf {
			state.code.length == 6 && state.code.all { it.isDigit() }
		}
	}
	val isNewPasswordValid by remember(state.newPassword) {
		derivedStateOf {
			state.newPassword.length >= 8
		}
	}
	val doPasswordsMatch by remember(state.newPassword, state.confirmPassword) {
		derivedStateOf {
			state.newPassword == state.confirmPassword
		}
	}

	Scaffold(
		containerColor = Color.White,
		topBar = {
			TopAppBar(
				title = { Text("Restablecer contraseña") },
				navigationIcon = {
					IconButton(onClick = { navController.popBackStack() }) {
						Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
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
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Text(text = "Email: $email", style = MaterialTheme.typography.bodyMedium)

			Spacer(modifier = Modifier.height(16.dp))

			OutlinedTextField(
				value = state.code,
				onValueChange = {
					if (it.length <= 6 && it.all { ch -> ch.isDigit() }) {
						viewModel.onCodeChange(it)
					}
				},
				label = { Text("Código recibido") },
				keyboardOptions = KeyboardOptions.Default.copy(
					imeAction = ImeAction.Next,
					keyboardType = KeyboardType.Number
				),
				modifier = Modifier.fillMaxWidth(),
				isError = !isCodeValid && state.code.isNotEmpty()
			)
			if (!isCodeValid && state.code.isNotEmpty()) {
				Text(
					"Debe tener 6 dígitos numéricos",
					color = MaterialTheme.colorScheme.error,
					style = MaterialTheme.typography.labelSmall,
					modifier = Modifier.fillMaxWidth()
				)
			}

			Spacer(modifier = Modifier.height(12.dp))

			OutlinedTextField(
				value = state.newPassword,
				onValueChange = viewModel::onNewPasswordChange,
				label = { Text("Nueva contraseña (mínimo 8 caracteres)") },
				visualTransformation = PasswordVisualTransformation(),
				keyboardOptions = KeyboardOptions.Default.copy(
					imeAction = ImeAction.Next,
					keyboardType = KeyboardType.Password
				),
				modifier = Modifier.fillMaxWidth(),
				isError = !isNewPasswordValid && state.newPassword.isNotEmpty()
			)
			if (!isNewPasswordValid && state.newPassword.isNotEmpty()) {
				Text(
					"Mínimo 8 caracteres",
					color = MaterialTheme.colorScheme.error,
					style = MaterialTheme.typography.labelSmall,
					modifier = Modifier.fillMaxWidth()
				)
			}

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
				modifier = Modifier.fillMaxWidth(),
				isError = !doPasswordsMatch && state.confirmPassword.isNotEmpty()
			)
			if (!doPasswordsMatch && state.confirmPassword.isNotEmpty()) {
				Text(
					"Las contraseñas no coinciden",
					color = MaterialTheme.colorScheme.error,
					style = MaterialTheme.typography.labelSmall,
					modifier = Modifier.fillMaxWidth()
				)
			}

			Spacer(modifier = Modifier.height(16.dp))

			if (state.isLoading) {
				CircularProgressIndicator()
			} else {
				Button(
					enabled = isCodeValid && isNewPasswordValid && doPasswordsMatch,
					onClick = {
						viewModel.submitReset(email) { success ->
							if (success) showSuccessDialog = true
						}
					},
					modifier = Modifier.fillMaxWidth()
				) {
					Text("Cambiar contraseña")
				}
			}

			state.errorMessage?.let {
				Spacer(modifier = Modifier.height(8.dp))
				Text(
					it,
					color = MaterialTheme.colorScheme.error,
					modifier = Modifier.fillMaxWidth()
				)
			}
		}
	}

	if (showSuccessDialog) {
		AlertDialog(
			onDismissRequest = {},
			title = { Text("Contraseña cambiada") },
			text = { Text("Tu contraseña fue actualizada con éxito.") },
			confirmButton = {
				TextButton(onClick = {
					showSuccessDialog = false
					navController.navigate("login") {
						popUpTo("login") { inclusive = true }
					}
				}) {
					Text("Aceptar")
				}
			}
		)
	}
}