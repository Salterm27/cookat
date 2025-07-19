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
	var showDialog by remember { mutableStateOf(false) }

	LaunchedEffect(email) {
		viewModel.setEmail(email)
	}

	// Validaciones locales para UI
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
				onValueChange = {
					if (it.length <= 6 && it.all { ch -> ch.isDigit() }) {
						viewModel.onCodeChange(it)
					}
				},
				label = { Text("Código de verificación") },
				keyboardOptions = KeyboardOptions.Default.copy(
					imeAction = ImeAction.Next,
					keyboardType = KeyboardType.Number
				),
				modifier = Modifier.fillMaxWidth(),
				isError = !isCodeValid && state.code.isNotEmpty()
			)
			if (!isCodeValid && state.code.isNotEmpty()) {
				Text(
					"El código debe tener 6 dígitos numéricos",
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
					"La contraseña debe tener al menos 8 caracteres",
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
						viewModel.submit {
							showDialog = true
						}
					},
					modifier = Modifier.fillMaxWidth()
				) {
					Text("Cambiar contraseña")
				}
			}

			// Mostrar error general que viene del backend u otra validación
			state.errorMessage?.let { errorMsg ->
				Spacer(modifier = Modifier.height(8.dp))
				Text(
					errorMsg,
					color = MaterialTheme.colorScheme.error,
					modifier = Modifier.fillMaxWidth()
				)
			}
		}
	}

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