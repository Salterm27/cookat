package com.example.cookat.screens.auth

import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordRequestScreen(
	navController: NavController
) {
	val context = LocalContext.current
	val viewModel: ForgotPasswordViewModel = viewModel(
		factory = object : ViewModelProvider.Factory {
			override fun <T : ViewModel> create(modelClass: Class<T>): T {
				val sessionManager = SessionManager(context)
				val repo = AuthRepository(sessionManager, context) // ✅ removed recipeDB
				@Suppress("UNCHECKED_CAST")
				return ForgotPasswordViewModel(repo) as T
			}
		}
	)

	val state by viewModel.uiState.collectAsState()
	var localError by remember { mutableStateOf<String?>(null) }

	Scaffold(
		containerColor = Color.White,
		topBar = {
			TopAppBar(
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
				onValueChange = {
					viewModel.onEmailChange(it)
					localError = null
				},
				label = { Text("Email") },
				modifier = Modifier.fillMaxWidth(),
				isError = localError != null
			)

			if (localError != null) {
				Text(
					text = localError!!,
					color = MaterialTheme.colorScheme.error,
					style = MaterialTheme.typography.labelSmall
				)
			}

			Spacer(modifier = Modifier.height(16.dp))

			Button(
				onClick = {
					if (!Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) {
						localError = "El email no es válido"
						return@Button
					}

					viewModel.submitRequest()
				},
				enabled = !state.isLoading,
				modifier = Modifier.fillMaxWidth()
			) {
				Text("Enviar código")
			}

			if (state.errorMessage != null) {
				Text(
					text = "Error: ${state.errorMessage}",
					color = MaterialTheme.colorScheme.error,
					modifier = Modifier.padding(top = 8.dp)
				)
			}

			if (state.success) {
				LaunchedEffect(Unit) {
					navController.navigate("reset_password/${state.email}")
				}
			}
		}
	}
}
