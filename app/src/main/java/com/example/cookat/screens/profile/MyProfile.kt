package com.example.cookat.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cookat.viewmodels.profile.UserProfileState

@Composable
fun MyProfile(
	uiState: UserProfileState,
	onUpdate: (String, String) -> Unit,
	onNavigateTo: () -> Unit
) {
	var email by remember { mutableStateOf(uiState.email) }
	var username by remember { mutableStateOf(uiState.username) }

	Scaffold { padding ->
		Column(
			modifier = Modifier
				.padding(padding)
				.fillMaxSize(),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Text(text = "Profile page")

			OutlinedTextField(
				value = email,
				onValueChange = { email = it },
				label = { Text("Email") }
			)

			OutlinedTextField(
				value = username,
				onValueChange = { username = it },
				label = { Text("Username") }
			)

			if (uiState.isLoading) {
				CircularProgressIndicator()
			} else {
				Button(onClick = {
					onUpdate(email, username)
					onNavigateTo()
				}) {
					Text("Update Profile")
				}
			}

			uiState.error?.let {
				Text("Error: $it", color = MaterialTheme.colorScheme.error)
			}
		}
	}
}
