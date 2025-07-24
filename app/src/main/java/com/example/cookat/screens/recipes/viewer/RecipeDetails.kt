package com.example.cookat.screens.recipes.viewer

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cookat.repository.RecipeRepository
import com.example.cookat.viewmodels.recipes.RecipeViewModel

@Composable
fun RecipeDetails(
	recipeId: String,
	navController: NavController
) {
	val context = LocalContext.current
	val repository = RecipeRepository(context)

	val viewModel: RecipeViewModel = viewModel(
		factory = object : ViewModelProvider.Factory {
			override fun <T : ViewModel> create(modelClass: Class<T>): T {
				if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
					@Suppress("UNCHECKED_CAST")
					return RecipeViewModel(repository, recipeId ) as T
				}
				throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
			}
		}
	)

	val state = viewModel.uiState

	RecipeDetailsContent(
		state = state,
		onBack = { navController.navigateUp() },
		onToggleFavorite = { viewModel.toggleFavourite() },
	)
}