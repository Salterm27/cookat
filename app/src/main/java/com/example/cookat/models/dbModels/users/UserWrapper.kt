package com.example.cookat.models.dbModels.users

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cookat.data.local.session.SessionManager
import com.example.cookat.network.BackendClient
import com.example.cookat.repository.UserRepository
import com.example.cookat.viewmodels.profile.ProfileViewModel

@Composable
fun UserWrapper() {
	val context = LocalContext.current

	// Creamos repo y factory
	val userRepository = remember {
		UserRepository(
			backendApi = BackendClient.create(context),
			sessionManager = SessionManager(context)
		)
	}

	val viewModel: ProfileViewModel = viewModel(
		factory = UserModelFactory(userRepository)
	)

	val uiState by viewModel.state.collectAsState()


}
