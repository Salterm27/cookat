package com.example.cookat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cookat.screens.auth.LogInScreen
import com.example.cookat.screens.auth.PasswordScreen
import com.example.cookat.screens.auth.RegisterScreen
import com.example.cookat.screens.auth.ValidatePasswordScreen
import com.example.cookat.screens.home.HomeScreen
import com.example.cookat.screens.profile.MyProfile
import com.example.cookat.screens.recipes.NewRecipe
import com.example.cookat.screens.settings.MySettings
import com.example.cookat.ui.theme.CookatTheme

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.cookat.screens.recipes.RecipeDetails


class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		enableEdgeToEdge()

		setContent {
			CookatTheme {
				AppNavigation()
			}
		}
	}
}

@Composable
fun AppNavigation() {
	val navController = rememberNavController()

	NavHost(navController = navController, startDestination = "login") {
		composable("login") {
			LogInScreen(
				onNavigateToRegister = { navController.navigate("register") },
				onLoginSuccess = { navController.navigate("auth") },
				onNavigateToPassword = { navController.navigate("password") })
		}
		composable("register") {
			RegisterScreen(onNavigateTo = { navController.navigate("login") })
		}
		composable("auth") {
			HomeScreen(navController)
		}
		composable("password") {
			PasswordScreen(onNavigateTo = { navController.navigate("validatePassword") })
		}
		composable("validatePassword") {
			ValidatePasswordScreen(onNavigateTo = { navController.navigate("login") })
		}

		composable("profile") {
			MyProfile(onNavigateTo = { navController.navigate("auth") })
		}

		composable("newRecipe") {
			NewRecipe(onNavigateTo = { navController.navigate("auth") })
		}

		composable(
			"recipe/{id}",
			arguments = listOf(navArgument("id") { type = NavType.StringType })
		) { backStackEntry ->
			val recipeId = backStackEntry.arguments?.getString("id") ?: ""
			RecipeDetails(id = recipeId, navController = navController)
		}

		composable("settings") {
			MySettings(onNavigateTo = { navController.navigate("auth") })
		}


	}
}

