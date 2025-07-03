package com.example.cookat.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cookat.screens.home.components.HomeContent
import com.example.cookat.screens.home.components.HomeTopBar
import com.example.cookat.screens.home.components.sidemenu.DrawerContent
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
				HomeTopBar(
					drawerState = drawerState,
					scope = scope
				)
			}
		) { padding ->
			HomeContent(
				modifier = Modifier.padding(padding),
				state = state,
				navController = navController
			)
		}
	}
}