package com.example.cookat.models.dbModels.users

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cookat.data.local.db.RecipeDB
import com.example.cookat.data.local.session.SessionManager
import com.example.cookat.network.BackendClient
import com.example.cookat.repository.UserRepository
import com.example.cookat.repository.AuthRepository
import com.example.cookat.screens.profile.MyProfile
import com.example.cookat.viewmodels.profile.ProfileViewModel

@Composable
fun ProfileScreen(
	onNavigateBack: () -> Unit,
	onNavigateTo: () -> Unit,
	onLogout: () -> Unit
) {
	val context = LocalContext.current
	val sessionManager = remember { SessionManager(context) }

	val userIdState = remember { mutableStateOf<String?>(null) }

	LaunchedEffect(Unit) {
		userIdState.value = sessionManager.getUserId()
	}

	val userId = userIdState.value

	if (userId != null) {
		val recipeDB = remember { RecipeDB.getDatabase(context, userId) }

		val userRepository = remember {
			UserRepository(
				backendApi = BackendClient.create(context),
				sessionManager = sessionManager
			)
		}

		val authRepository = remember {
			AuthRepository(
				sessionManager = sessionManager,
				context = context
			)
		}

		val viewModel: ProfileViewModel = viewModel(
			factory = UserModelFactory(userRepository, authRepository)
		)

		MyProfile(
			viewModel = viewModel,
			onNavigateBack = onNavigateBack,
			onNavigateTo = onNavigateTo,
			onLogout = onLogout
		)
	} else {
		// Optional: show a loading indicator
	}
}