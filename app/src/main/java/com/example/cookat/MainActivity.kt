package com.example.cookat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cookat.data.local.session.SessionManager
import com.example.cookat.data.remote.SupabaseClient
import com.example.cookat.models.dbModels.users.ProfileScreen
import com.example.cookat.network.BackendClient
import com.example.cookat.repository.AuthRepository
import com.example.cookat.screens.auth.ForgotPasswordRequestScreen
import com.example.cookat.screens.auth.LogInScreen
import com.example.cookat.screens.auth.PasswordScreen
import com.example.cookat.screens.auth.RegisterScreen
import com.example.cookat.screens.auth.ResetPasswordScreen
import com.example.cookat.screens.auth.ValidatePasswordScreen
import com.example.cookat.screens.home.HomeScreen
import com.example.cookat.screens.recipes.editor.RecipeEditor
import com.example.cookat.screens.recipes.viewer.RecipeDetails
import com.example.cookat.screens.settings.MySettings
import com.example.cookat.ui.theme.CookatTheme
import com.example.cookat.viewmodels.auth.LoginViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		enableEdgeToEdge()

		// Wake up the backend server with a HEAD ping
		wakeUpBackend()

		setContent {
			CookatTheme {
				AppNavigation()
			}
		}
	}

	private fun wakeUpBackend() {
		val api = BackendClient.create(this)
		GlobalScope.launch {
			try {
				api.pingBackend()
			} catch (e: Exception) {
				e.printStackTrace()
			}
		}
	}
}

@Composable
fun AppNavigation() {
	val navController = rememberNavController()
	val context = LocalContext.current

	NavHost(navController = navController, startDestination = "start") {

		composable("start") {
			val context = LocalContext.current
			val sessionManager = remember { SessionManager(context) }

			LaunchedEffect(Unit) {
				val supabase = SupabaseClient.client
				val validToken = sessionManager.getValidAccessToken(supabase)

				if (validToken != null) {
					navController.navigate("home") {
						popUpTo("start") { inclusive = true }
					}
				} else {
					navController.navigate("login") {
						popUpTo("start") { inclusive = true }
					}
				}
			}
		}



		composable("login") {
			val viewModel: LoginViewModel = viewModel(
				factory = object : ViewModelProvider.Factory {
					override fun <T : ViewModel> create(modelClass: Class<T>): T {
						val sessionManager = SessionManager(context) // ✅ 1. create it!
						val repo =
							AuthRepository(sessionManager, context = context) // ✅ 2. pass it in
						return LoginViewModel(repo) as T
					}
				}
			)

			LogInScreen(
				viewModel = viewModel,
				onNavigateToRegister = { navController.navigate("register") },
				onLoginSuccess = { navController.navigate("home") },
				onNavigateToPassword = { navController.navigate("forgot_password") }
			)
		}

		composable("register") {
			RegisterScreen(
				onNavigateTo = { navController.navigate("login") },
				onNavigateBack = { navController.navigate("login") }
			)
		}

		composable("home") {
			HomeScreen(navController)
		}

		composable("forgot_password") {
			ForgotPasswordRequestScreen(navController = navController)
		}

		composable(
			"reset_password/{email}",
			arguments = listOf(navArgument("email") { type = NavType.StringType })
		) { backStackEntry ->
			ResetPasswordScreen(backStackEntry = backStackEntry, navController = navController)
		}



		composable("password") {
			PasswordScreen(onNavigateTo = { navController.navigate("validatePassword") })
		}

		composable("validatePassword") {
			ValidatePasswordScreen(onNavigateTo = { navController.navigate("login") })
		}

		composable("profile") {
			ProfileScreen(
				onNavigateTo = { navController.navigate("home") },
				onNavigateBack = { navController.navigate("home") }
			)
		}

		composable(
			"recipe/{id}/{isFavourite}",
			arguments = listOf(navArgument("id") { type = NavType.StringType })
		) { backStackEntry ->
			val recipeId = backStackEntry.arguments?.getString("id") ?: ""
			val isFavourite = backStackEntry.arguments?.getString("isFavourite") ?: ""
			RecipeDetails(recipeId = recipeId, navController = navController, isFavourite = isFavourite.toBoolean())
		}

		composable("settings") {
			MySettings(onNavigateTo = { navController.navigate("home") })
		}

		composable(
			"recipe_editor/{recipeName}",
			arguments = listOf(navArgument("recipeName") { type = NavType.StringType })
		) { backStackEntry ->
			// decode to support spaces and special chars
			val recipeName = backStackEntry.arguments?.getString("recipeName")?.let {
				URLDecoder.decode(it, StandardCharsets.UTF_8.name())
			} ?: ""
			RecipeEditor(
				recipeName = recipeName,
				onFinish = { navController.navigate("home") },   // After final step
				onCancel = { navController.popBackStack() }      // If user cancels editing
			)
		}
	}
}