package com.example.cookat.viewmodels.recipes

import android.util.Log
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
	private val recipeId: String,
	private val isFavourite: Boolean

) : ViewModel() {

	var uiState by mutableStateOf(RecipeUiState())
		private set

	init {
		loadRecipe(recipeId, isFavourite = false)
	}

	fun loadRecipe(recipeId: String, isFavourite: Boolean) {
		viewModelScope.launch {
			uiState = uiState.copy(isLoading = true)
			uiState = uiState.copy(isFav = isFavourite)

			val result = repository.getRecipeById(recipeId)
			Log.d("RecipeViewModel", "Loaded recipe: $result")
			uiState = if (result.isSuccess) {
				uiState.copy(isLoading = false, recipe = result.getOrNull())

			} else {
				uiState.copy(isLoading = false, errorMessage = result.exceptionOrNull()?.message)
			}
		}
	}

	fun toggleFavourite() {
		viewModelScope.launch {
			val recipe = uiState.recipe ?: return@launch
			val newState = !recipe.isFavourite

			repository.updateFavouriteLocal(recipe.id, newState)
			uiState = uiState.copy(recipe = recipe.copy(isFavourite = newState))

			runCatching {
				if (newState) repository.apiAddFavourite(recipe.id)
				else repository.apiRemoveFavourite(recipe.id)
			}
		}
	}
}