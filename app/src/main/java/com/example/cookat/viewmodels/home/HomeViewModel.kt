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
			// Show loading while loading local
			uiState = uiState.copy(isLoading = true)

			// Load local recipes first
			val localRecipes = repository.getLocalRecipes()
			uiState = uiState.copy(
				isLoading = false,
				recipes = localRecipes
			)

			// Then refresh from backend sequentially
			try {
				repository.refreshRecipesFromBackend()

				// Load local again to get updated data
				val updatedRecipes = repository.getLocalRecipes()
				uiState = uiState.copy(
					recipes = updatedRecipes
				)

			} catch (e: Exception) {
				uiState = uiState.copy(
					errorMessage = e.message
				)
			}
		}
	}
}