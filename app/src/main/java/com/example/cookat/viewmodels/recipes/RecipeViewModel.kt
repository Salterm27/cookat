package com.example.cookat.viewmodels.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookat.models.uiStates.RecipeUiState
import com.example.cookat.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeViewModel(
	private val repository: RecipeRepository,
	private val recipeId: String
) : ViewModel() {

	private val _uiState = MutableStateFlow(RecipeUiState())
	val uiState: StateFlow<RecipeUiState> = _uiState

	// ✅ VISUAL-ONLY FAVORITE TOGGLE
	private val _isFavorite = MutableStateFlow(false)
	val isFavorite: StateFlow<Boolean> = _isFavorite

	init {
		loadRecipe()
	}

	private fun loadRecipe() {
		viewModelScope.launch {
			_uiState.value = _uiState.value.copy(isLoading = true)

			val result = repository.getRecipeById(recipeId)

			_uiState.value = if (result.isSuccess) {
				_uiState.value.copy(
					isLoading = false,
					recipe = result.getOrNull()
				)
			} else {
				_uiState.value.copy(
					isLoading = false,
					errorMessage = result.exceptionOrNull()?.message ?: "Unknown error"
				)
			}
		}
	}

	fun toggleFavorite() {
		viewModelScope.launch {
			_isFavorite.value = !_isFavorite.value

			// ✅ TODO:
			// - Wire this up to your backend: add/remove favorite.
			// - Update your local DB if needed.
			// - Rollback local change if API fails.
		}
	}
}