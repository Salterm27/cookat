package com.example.cookat.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cookat.screens.home.sidemenu.DrawerContent
import com.example.cookat.viewmodels.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

	val context = LocalContext.current

	val viewModel: HomeViewModel = viewModel(factory = object : ViewModelProvider.Factory {
		override fun <T : ViewModel> create(modelClass: Class<T>): T {
			return HomeViewModel(context.applicationContext) as T
		}
	})

	val drawerState = rememberDrawerState(DrawerValue.Closed)
	val scope = rememberCoroutineScope()
	val state = viewModel.uiState

	ModalNavigationDrawer(
		drawerState = drawerState,
		drawerContent = { DrawerContent(navController) }
	) {
		Scaffold(
			topBar = {
				// TODO: Add your TopAppBar here if you want it
			}
		) { padding ->
			Column(
				modifier = Modifier
					.padding(padding)
					.padding(16.dp)
					.fillMaxSize()
			) {
				when {
					state.isLoading -> {
						Text("Loading...")
					}

					state.errorMessage != null -> {
						Text("Error: ${state.errorMessage}")
					}

					else -> {
						state.recipes.forEach { recipe ->
							Text(text = recipe.title)
							Spacer(modifier = Modifier.height(8.dp))
						}
					}
				}
			}
		}
	}
}