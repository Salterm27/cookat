package com.example.cookat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.cookat.screens.auth.LogInScreen
import com.example.cookat.ui.theme.CookatTheme
import com.example.cookat.screens.auth.RegisterScreen
import com.example.cookat.screens.home.HomeScreen
import com.example.cookat.screens.auth.PasswordScreen
import com.example.cookat.screens.auth.ValidatePasswordScreen
import com.example.cookat.screens.profile.MyProfile
import com.example.cookat.screens.settings.MySettings
import com.example.cookat.screens.recipes.NewRecipe



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
			LogInScreen(onNavigateToRegister = { navController.navigate("register")},
				onNavigateToAuth = { navController.navigate("auth") },
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
			MyProfile(onNavigateTo = {navController.navigate("auth") } )
		}

		composable ("newRecipe") {
			NewRecipe(onNavigateTo = { navController.navigate("auth") })
		}

		composable("settings") {
			MySettings(onNavigateTo = { navController.navigate("auth") })
		}


		}
}

