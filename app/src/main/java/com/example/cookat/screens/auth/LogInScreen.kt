package com.example.cookat.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.cookat.R
import com.example.cookat.viewmodels.auth.LoginViewModel

@Composable
fun LogInScreen(
	viewModel: LoginViewModel,
	onNavigateToRegister: () -> Unit,
	onLoginSuccess: (String) -> Unit,
	onNavigateToPassword: () -> Unit
) {
	val state = viewModel.uiState

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(32.dp),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(
			text = "Cookat",
			style = MaterialTheme.typography.displayLarge.copy(
				color = MaterialTheme.colorScheme.primary
			)
		)

		Spacer(Modifier.height(16.dp))

		Image(
			painter = painterResource(R.drawable.cookat_logo),
			contentDescription = "Cookat Logo",
			modifier = Modifier.size(150.dp)
		)

		Spacer(Modifier.height(32.dp))

		OutlinedTextField(
			value = state.email,
			onValueChange = { viewModel.onEmailChange(it) },
			label = { Text("User Account") },
			placeholder = { Text("Input") },
			trailingIcon = {
				if (state.email.isNotEmpty()) {
					IconButton(onClick = { viewModel.onEmailChange("") }) {
						Icon(Icons.Default.Close, contentDescription = "Clear Email")
					}
				}
			},
			singleLine = true,
			shape = RoundedCornerShape(12.dp),
			modifier = Modifier.fillMaxWidth(),
			colors = OutlinedTextFieldDefaults.colors(
				focusedBorderColor = MaterialTheme.colorScheme.primary,
				unfocusedBorderColor = MaterialTheme.colorScheme.outline,
				cursorColor = MaterialTheme.colorScheme.primary
			),
			keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
		)

		Spacer(Modifier.height(16.dp))

		OutlinedTextField(
			value = state.password,
			onValueChange = { viewModel.onPasswordChange(it) },
			label = { Text("Password") },
			placeholder = { Text("Input") },
			trailingIcon = {
				if (state.password.isNotEmpty()) {
					IconButton(onClick = { viewModel.onPasswordChange("") }) {
						Icon(Icons.Default.Close, contentDescription = "Clear Password")
					}
				}
			},
			singleLine = true,
			shape = RoundedCornerShape(12.dp),
			modifier = Modifier.fillMaxWidth(),
			colors = OutlinedTextFieldDefaults.colors(
				focusedBorderColor = MaterialTheme.colorScheme.primary,
				unfocusedBorderColor = MaterialTheme.colorScheme.outline,
				cursorColor = MaterialTheme.colorScheme.primary
			),
			keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
			visualTransformation = PasswordVisualTransformation() // ✅ Mask the password!
		)

		Spacer(Modifier.height(8.dp))

		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			TextButton(onClick = onNavigateToPassword) {
				Text("Olvide mi contraseña", color = MaterialTheme.colorScheme.primary)
			}

			TextButton(onClick = onNavigateToRegister) {
				Text("No tenes una cuenta? Registrate!", color = MaterialTheme.colorScheme.primary)
			}
		}

		Spacer(Modifier.height(32.dp))

		Button(
			onClick = {
				viewModel.login { userId ->
					onLoginSuccess(userId) // ← pass userId to next step (e.g. build DB, navigate)
				}
			},
			shape = RoundedCornerShape(50),
			modifier = Modifier.align(Alignment.End),
			colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
		) {
			Text("Entrar a la App", color = MaterialTheme.colorScheme.onPrimary)
		}

		state.errorMessage?.let {
			Spacer(Modifier.height(16.dp))
			Text(it, color = MaterialTheme.colorScheme.error)
		}
	}
}