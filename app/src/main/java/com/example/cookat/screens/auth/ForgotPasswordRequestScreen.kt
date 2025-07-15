package com.example.cookat.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cookat.data.local.session.SessionManager
import com.example.cookat.repository.AuthRepository
import com.example.cookat.screens.auth.components.SmallTopAppBar
import com.example.cookat.viewmodels.auth.ForgotPasswordViewModel

@Composable
fun ForgotPasswordRequestScreen(
	navController: NavController
) {
	val context = LocalContext.current
	val viewModel: ForgotPasswordViewModel = viewModel(
		factory = object : ViewModelProvider.Factory {
			override fun <T : ViewModel> create(modelClass: Class<T>): T {
				@Suppress("UNCHECKED_CAST")
				val sessionManager = SessionManager(context)
				val repo = AuthRepository(sessionManager, context)
				return ForgotPasswordViewModel(repo) as T
			}
		}
	)

	val state by viewModel.uiState.collectAsState()

	Scaffold(
		topBar = {
			SmallTopAppBar(
				title = { Text("Recuperar contraseña") },
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
				.padding(24.dp),
			verticalArrangement = Arrangement.Center
		) {
			OutlinedTextField(
				value = state.email,
				onValueChange = { viewModel.onEmailChange(it) },
				label = { Text("Email") },
				modifier = Modifier.fillMaxWidth()
			)

			Spacer(modifier = Modifier.height(16.dp))

			Button(
				onClick = {
					viewModel.submitRequest()
				},
				enabled = !state.isLoading,
				modifier = Modifier.fillMaxWidth()
			) {
				Text("Enviar código")
			}

			if (state.errorMessage != null) {
				Text(text = "Error: ${state.errorMessage}", color = MaterialTheme.colorScheme.error)
			}

			if (state.success) {
				LaunchedEffect(Unit) {
					// Navegamos pasando el email para la siguiente pantalla
					navController.navigate("reset_password/${state.email}")
				}
			}
		}
	}
}
