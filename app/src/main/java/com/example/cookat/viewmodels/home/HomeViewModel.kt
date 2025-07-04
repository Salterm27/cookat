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
		observeRecipes()
		refreshRecipes()
	}

	private fun observeRecipes() {
		viewModelScope.launch {
			repository.observeRecipes().collect { recipes ->
				uiState = uiState.copy(recipes = recipes)
			}
		}
	}

	fun refreshRecipes() {
		viewModelScope.launch {
			uiState = uiState.copy(isLoading = true)
			runCatching { repository.refreshRecipesFromBackend() }
				.onFailure {
					uiState = uiState.copy(errorMessage = it.message)
				}
			uiState = uiState.copy(isLoading = false)
		}
	}

	fun toggleFavourite(recipeId: String, newState: Boolean) {
		viewModelScope.launch {
			repository.toggleFavourite(recipeId, newState)
			
		}
	}
}