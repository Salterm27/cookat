package com.example.cookat.screens.profile


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cookat.viewmodels.profile.ProfileViewModel

@Composable
fun MyProfile(
	onNavigateTo: () -> Unit
) {
	val viewModel: ProfileViewModel = viewModel()
	val state by viewModel.state.collectAsState()

	var email by remember { mutableStateOf(state.email) }
	var username by remember { mutableStateOf(state.username) }

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

			if (state.isLoading) {
				CircularProgressIndicator()
			} else {
				Button(onClick = {
					viewModel.updateProfile(email, username)
					onNavigateTo()
				}) {
					Text("Update Profile")
				}
			}

			state.error?.let {
				Text("Error: $it")
			}
		}
	}
}