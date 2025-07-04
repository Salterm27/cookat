package com.example.cookat.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cookat.data.local.session.SessionManager
import com.example.cookat.network.BackendClient
import com.example.cookat.repository.AuthRepository
import com.example.cookat.repository.UserRepository
import com.example.cookat.viewmodels.profile.RegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
	onNavigateTo: () -> Unit,
	onNavigateBack: () -> Unit
) {
	val context = LocalContext.current

	val viewModel: RegisterViewModel = viewModel {
		RegisterViewModel(
			authRepository = AuthRepository(SessionManager(context)),
			userRepository = UserRepository(
				backendApi = BackendClient.create(context),
				sessionManager = SessionManager(context)
			)
		)
	}

	val state by viewModel.uiState.collectAsState()

	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text("Crear cuenta") },
				navigationIcon = {
					IconButton(onClick = onNavigateBack) {
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
				Button(onClick = {
					viewModel.register {
						onNavigateTo()
					}
				}) {
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