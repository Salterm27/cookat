package com.example.cookat.viewmodels.home

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cookat.models.uiStates.HomeUiState
import com.example.cookat.models.uiStates.RecipeFilter
import com.example.cookat.repository.HomeRepository
import kotlinx.coroutines.launch
import java.util.Locale.getDefault

class HomeViewModel(
	private val repository: HomeRepository
) : ViewModel() {

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

	fun showNewRecipeDialog() {
		uiState =
			uiState.copy(showNewRecipeDialog = true, recipeNameError = null, pendingRecipeName = "")
	}

	fun hideNewRecipeDialog() {
		uiState = uiState.copy(showNewRecipeDialog = false, recipeNameError = null)
	}

	fun onRecipeNameChange(name: String) {
		uiState = uiState.copy(pendingRecipeName = name)
	}

	fun submitNewRecipeName() {
		val name = uiState.pendingRecipeName.trim()
		if (name.isEmpty()) {
			uiState = uiState.copy(recipeNameError = "La receta necesita un titulo")
			return
		}

		viewModelScope.launch {
			uiState = uiState.copy(isCheckingRecipeName = true)
			Log.d("HomeViewModel", "Checking recipe name: $name")

			val result = repository.recipeExistsRemotely(name)
			Log.d("HomeViewModel", "Recipe name check result: $result")

			uiState = when {
				result.isSuccess && result.getOrNull() == true -> {
					// 200: exists
					uiState.copy(
						isCheckingRecipeName = false,
						showNewRecipeDialog = false,
						showNameExistsDialog = true
					)
				}

				result.isSuccess && result.getOrNull() == false -> {
					// 404: does not exist
					uiState.copy(
						isCheckingRecipeName = false,
						showNewRecipeDialog = false,
						navigateToRecipeEditor = name
					)
				}

				else -> {
					// Any other error
					uiState.copy(
						isCheckingRecipeName = false,
						showNewRecipeDialog = false,
						errorMessage = "No pudimos validar el nombre de la receta. Intentalo otra vez."
					)
				}
			}
		}
	}


	fun fakeSubmitNewRecipeName() {
		val name = uiState.pendingRecipeName.trim()
		if (name.isEmpty()) {
			uiState = uiState.copy(recipeNameError = "La receta necesita un titulo")
			return
		}

		viewModelScope.launch {
			val fakeExists = name.lowercase(getDefault()) == "tortilla"

			uiState = if (fakeExists) {
				uiState.copy(
					isCheckingRecipeName = false,
					showNewRecipeDialog = false,
					showNameExistsDialog = true
				)
			} else {
				uiState.copy(
					isCheckingRecipeName = false,
					showNewRecipeDialog = false,
					navigateToRecipeEditor = name
				)
			}

		}
	}

	fun hideNameExistsDialog() {
		uiState = uiState.copy(showNameExistsDialog = false)
	}

	// For Replace/Modify/Create actions:
	fun onReplaceRecipe() { /* your logic here */
	}

	fun onModifyRecipe() { /* your logic here */
	}

	fun onNavigatedToRecipeEditor() {
		uiState = uiState.copy(navigateToRecipeEditor = null)
	}


}


class HomeViewModelFactory(
	private val repository: HomeRepository
) : ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
			@Suppress("UNCHECKED_CAST")
			return HomeViewModel(repository) as T
		}
		throw IllegalArgumentException("Unknown ViewModel class")
	}
}