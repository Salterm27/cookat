package com.example.cookat.models.dbModels.users

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cookat.data.local.session.SessionManager
import com.example.cookat.network.BackendClient
import com.example.cookat.repository.UserRepository
import com.example.cookat.viewmodels.profile.ProfileViewModel
import com.example.cookat.models.dbModels.users.UserModelFactory
import com.example.cookat.screens.profile.MyProfile

@Composable
fun ProfileScreen(
	onNavigateTo: () -> Unit
) {
	val context = LocalContext.current

	val userRepository = remember {
		UserRepository(
			backendApi = BackendClient.create(context),
			sessionManager = SessionManager(context)
		)
	}

	val viewModel: ProfileViewModel = viewModel(
		factory = UserModelFactory(userRepository)
	)

	val state by viewModel.state.collectAsState()

	MyProfile(
		uiState = state,
		onUpdate = { email, username -> viewModel.updateProfile(email, username) },
		onNavigateTo = onNavigateTo
	)
}
