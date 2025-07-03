package com.example.cookat.models.uiStates

import com.example.cookat.models.dbModels.recipes.RecipeModel

enum class RecipeFilter {
	ALL, FAVOURITES, MINE, SEARCH
}

data class HomeUiState(
	val isLoading: Boolean = false,
	val recipes: List<RecipeModel> = emptyList(),
	val errorMessage: String? = null,
	val currentFilter: RecipeFilter = RecipeFilter.ALL
)