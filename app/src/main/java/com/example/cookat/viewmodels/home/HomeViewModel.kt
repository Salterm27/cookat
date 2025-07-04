package com.example.cookat.viewmodels.home

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookat.models.uiStates.HomeUiState
import com.example.cookat.repository.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel(context: Context) : ViewModel() {

	private val repository = HomeRepository(context)

	var uiState by mutableStateOf(HomeUiState())
		private set

	init {
		loadRecipes()
	}

	fun loadRecipes() {
		viewModelScope.launch {
			uiState = uiState.copy(isLoading = true)

			val localRecipes = repository.getLocalRecipes()
			uiState = uiState.copy(isLoading = false, recipes = localRecipes)

			try {
				repository.refreshRecipesFromBackend()
				val updatedRecipes = repository.getLocalRecipes()
				uiState = uiState.copy(recipes = updatedRecipes)
			} catch (e: Exception) {
				uiState = uiState.copy(errorMessage = e.message)
			}
		}
	}

	fun toggleFavourite(recipeId: String, newState: Boolean) {
		viewModelScope.launch {
			try {
				repository.toggleFavourite(recipeId, newState)

				
				val updatedRecipes = uiState.recipes.map { recipe ->
					if (recipe.id == recipeId) recipe.copy(isFavourite = newState)
					else recipe
				}

				uiState = uiState.copy(recipes = updatedRecipes)

			} catch (e: Exception) {
				uiState = uiState.copy(errorMessage = e.message)
			}
		}
	}
}