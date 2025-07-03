package com.example.cookat.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cookat.data.local.session.SessionManager
import com.example.cookat.network.BackendClient
import com.example.cookat.repository.AuthRepository
import com.example.cookat.repository.UserRepository
import com.example.cookat.viewmodels.profile.RegisterViewModel

@Composable
fun RegisterScreen(onNavigateTo: () -> Unit) {
	val context = LocalContext.current
	val sessionManager = remember { SessionManager(context) }
	val viewModel: RegisterViewModel = viewModel {
		RegisterViewModel(
			authRepository = AuthRepository(sessionManager),
			userRepository = UserRepository(
				backendApi = BackendClient.create(context),
				sessionManager = SessionManager(context)
			)
		)
	}

	val state by viewModel.uiState.collectAsState()

	Scaffold { padding ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(padding)
				.padding(horizontal = 24.dp),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			TextField(
				value = state.email,
				onValueChange = viewModel::onEmailChange,
				label = { Text("Email") },
				modifier = Modifier.fillMaxWidth()
			)

			Spacer(modifier = Modifier.height(12.dp))

			TextField(
				value = state.password,
				onValueChange = viewModel::onPasswordChange,
				label = { Text("Contraseña") },
				visualTransformation = PasswordVisualTransformation(),
				modifier = Modifier.fillMaxWidth(),
				keyboardOptions = KeyboardOptions.Default.copy(
					imeAction = ImeAction.Next,
					keyboardType = KeyboardType.Password
				)
			)

			Spacer(modifier = Modifier.height(12.dp))

			TextField(
				value = state.confirmPassword,
				onValueChange = viewModel::onConfirmPasswordChange,
				label = { Text("Confirmar contraseña") },
				visualTransformation = PasswordVisualTransformation(),
				modifier = Modifier.fillMaxWidth(),
				keyboardOptions = KeyboardOptions.Default.copy(
					imeAction = ImeAction.Done,
					keyboardType = KeyboardType.Password
				)
			)

			Spacer(modifier = Modifier.height(16.dp))

			if (state.isLoading) {
				CircularProgressIndicator()
			} else {
				Button(onClick = { viewModel.register(onNavigateTo) }) {
					Text("Registrarse")
				}
			}

			state.error?.let {
				Spacer(modifier = Modifier.height(8.dp))
				Text(it, color = MaterialTheme.colorScheme.error)
			}
		}
	}
}
