package com.example.cookat.viewmodels.recipes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookat.models.uiStates.RecipeUiState
import com.example.cookat.repository.RecipeRepository
import kotlinx.coroutines.launch

class RecipeViewModel(
	private val repository: RecipeRepository,
	private val recipeId: String
) : ViewModel() {

	var uiState by mutableStateOf(RecipeUiState())
		private set

	init {
		loadRecipe(recipeId)
	}

	fun loadRecipe(recipeId: String) {
		viewModelScope.launch {
			uiState = uiState.copy(isLoading = true)

			val result = repository.getRecipeById(recipeId)
			uiState = if (result.isSuccess) {
				uiState.copy(isLoading = false, recipe = result.getOrNull())
			} else {
				uiState.copy(isLoading = false, errorMessage = result.exceptionOrNull()?.message)
			}
		}
	}

	fun toggleFavorite() {
		viewModelScope.launch {
			val recipe = uiState.recipe ?: return@launch
			val newState = !recipe.isFavourite

			// Instantly update local DB
			repository.updateFavouriteLocal(recipe.id, newState)

			// Optimistically update UI
			uiState = uiState.copy(recipe = recipe.copy(isFavourite = newState))

			// Fire backend call (don’t block UI)
			runCatching {
				if (newState) {
					repository.apiAddFavourite(recipe.id)
				} else {
					repository.apiRemoveFavourite(recipe.id)
				}
			}.onFailure {
				// Log or handle retry
			}
		}
	}
}