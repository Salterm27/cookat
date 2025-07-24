package com.example.cookat.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cookat.data.local.db.RecipeDB
import com.example.cookat.network.BackendClient
import com.example.cookat.repository.HomeRepository
import com.example.cookat.screens.home.components.HomeContent
import com.example.cookat.screens.home.components.HomeTopBar
import com.example.cookat.screens.home.components.dialogs.HomeDialogs
import com.example.cookat.screens.home.components.sidemenu.DrawerContent
import com.example.cookat.viewmodels.home.HomeViewModel
import com.example.cookat.viewmodels.home.HomeViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, recipeDB: RecipeDB) {
	val context = LocalContext.current

	val viewModel: HomeViewModel = viewModel(
		factory = remember {
			val repository = HomeRepository(
				api = BackendClient.create(context),
				dao = recipeDB.recipeDao()
			)
			HomeViewModelFactory(repository)
		}
	)

	val drawerState = rememberDrawerState(DrawerValue.Closed)
	val scope = rememberCoroutineScope()
	val state = viewModel.uiState

	LaunchedEffect(state.navigateToRecipeEditor) {
		state.navigateToRecipeEditor?.let { recipeName ->
			// Replace with your real navigation route and parameter
			navController.navigate("recipe_editor/${recipeName}")
			viewModel.onNavigatedToRecipeEditor() // Reset trigger!
		}
	}

	ModalNavigationDrawer(
		drawerState = drawerState,
		drawerContent = {
			DrawerContent(navController, onNewRecipeClick = { viewModel.showNewRecipeDialog() })
		}
	) {
		Scaffold(
			topBar = {
				HomeTopBar(
					drawerState = drawerState,
					scope = scope,
					currentFilter = state.currentFilter,
					searchQuery = state.searchQuery,
					onFilterChange = { filter -> viewModel.setFilter(filter) },
					onSearchChange = { query -> viewModel.setSearchQuery(query) }
				)
			}
		) { padding ->
			HomeContent(
				modifier = Modifier.padding(padding),
				state = state,
				navController = navController,
				onToggleFavorite = { recipeId, newState ->
					viewModel.toggleFavourite(recipeId, newState)
				},
				onLoadMore = { lastVisible, totalItems ->
					viewModel.loadMoreIfNeeded(lastVisible, totalItems)
				}
			)

		}
		HomeDialogs(state = state, viewModel = viewModel)
	}
}