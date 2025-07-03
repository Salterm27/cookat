package com.example.cookat.models.dbModels.users

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cookat.data.local.session.SessionManager
import com.example.cookat.network.BackendClient
import com.example.cookat.repository.UserRepository
import com.example.cookat.screens.profile.MyProfile
import com.example.cookat.viewmodels.profile.ProfileViewModel

@Composable
fun ProfileScreen(
	onNavigateBack: () -> Unit,
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

	MyProfile(
		viewModel = viewModel,
		onNavigateBack = onNavigateBack,
		onNavigateTo = onNavigateTo
	)
}