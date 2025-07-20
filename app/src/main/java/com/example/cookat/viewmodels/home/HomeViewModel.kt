package com.example.cookat.viewmodels.home

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookat.models.uiStates.HomeUiState
import com.example.cookat.models.uiStates.RecipeFilter
import com.example.cookat.repository.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel(context: Context) : ViewModel() {
	private val repository = HomeRepository(context)
	var uiState by mutableStateOf(HomeUiState())
		private set
	private var currentPage = 1
	private var isLoadingMore = false

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
			currentPage = 1
			runCatching {
				repository.refreshRecipesFromBackend()
			}.onFailure {
				uiState = uiState.copy(errorMessage = it.message)
			}
			uiState = uiState.copy(isLoading = false)
		}
	}

	fun loadMoreIfNeeded(lastVisibleItemIndex: Int, totalItemsCount: Int) {
		if (isLoadingMore) return

		if (lastVisibleItemIndex >= totalItemsCount - 5) {
			isLoadingMore = true
			currentPage += 1

			viewModelScope.launch {
				runCatching {
					repository.fetchRecipesPage(currentPage)
				}
				isLoadingMore = false
			}
		}
	}

	fun toggleFavourite(recipeId: String, newState: Boolean) {
		viewModelScope.launch {
			repository.toggleFavourite(recipeId, newState)
			// Local Room Flow will emit updated recipes automatically
		}
	}

	fun setFilter(filter: RecipeFilter) {
		uiState = uiState.copy(currentFilter = filter)
	}

	fun setSearchQuery(query: String) {
		uiState = uiState.copy(searchQuery = query)
	}
}