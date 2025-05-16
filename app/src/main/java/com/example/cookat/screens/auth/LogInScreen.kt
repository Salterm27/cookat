package com.example.cookat.screens.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cookat.ui.theme.Error
import com.example.cookat.viewmodels.auth.LoginViewModel

@Composable
fun LogInScreen(
	viewModel: LoginViewModel = viewModel(),
	onLoginSuccess: () -> Unit,
	onNavigateToRegister: () -> Unit,
	onNavigateToPassword: () -> Unit,
) {
	val state = viewModel.uiState
	val focusManager = LocalFocusManager.current

	val emailFocusRequester = remember { FocusRequester() }
	val passwordFocusRequester = remember { FocusRequester() }

	Scaffold { padding ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(padding)
				.padding(horizontal = 24.dp),
			verticalArrangement = Arrangement.Center
		) {
			
			TextField(
				value = state.email,
				onValueChange = viewModel::onEmailChange,
				label = { Text("Email") },
				modifier = Modifier
					.fillMaxWidth()
					.focusRequester(emailFocusRequester),
				keyboardOptions = KeyboardOptions.Default.copy(
					imeAction = ImeAction.Next,
					keyboardType = KeyboardType.Email
				),
				keyboardActions = KeyboardActions(
					onNext = { passwordFocusRequester.requestFocus() }
				)
			)

			Spacer(modifier = Modifier.height(12.dp))

			TextField(
				value = state.password,
				onValueChange = viewModel::onPasswordChange,
				label = { Text("contraseña") },
				visualTransformation = PasswordVisualTransformation(),
				modifier = Modifier
					.fillMaxWidth()
					.focusRequester(passwordFocusRequester),
				keyboardOptions = KeyboardOptions.Default.copy(
					imeAction = ImeAction.Done,
					keyboardType = KeyboardType.Password
				),
				keyboardActions = KeyboardActions(
					onDone = {
						focusManager.clearFocus()
						viewModel.login(onLoginSuccess)
					}
				)
			)

			Spacer(modifier = Modifier.height(8.dp))

			Text(
				text = "Olvidaste la contraseña?",
				color = MaterialTheme.colorScheme.primary,
				style = MaterialTheme.typography.bodySmall,
				modifier = Modifier
					.align(Alignment.End)
					.clickable(onClick = onNavigateToPassword)
					.padding(4.dp)
			)

			Spacer(modifier = Modifier.height(24.dp))

			if (state.isLoading) {
				CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
			} else {
				Button(
					onClick = { viewModel.login(onLoginSuccess) },
					modifier = Modifier
						.fillMaxWidth()
						.height(48.dp)
				) {
					Text("Entrar a la app")
				}

				state.errorMessage?.let {
					Spacer(modifier = Modifier.height(8.dp))
					Text(
						text = it,
						color = Error,
						style = MaterialTheme.typography.bodySmall
					)
				}

				Spacer(modifier = Modifier.height(16.dp))



				Spacer(modifier = Modifier.height(12.dp))

				TextButton(
					onClick = onNavigateToRegister,
					modifier = Modifier.align(Alignment.CenterHorizontally)
				) {
					Text("No tenes una cuenta? Registrate!")
				}
			}
		}
	}
}