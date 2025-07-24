package com.example.cookat.models.uiStates

import com.example.cookat.models.dbModels.recipes.RecipeModel

data class RecipeUiState(
	val isLoading: Boolean = false,
	val errorMessage: String? = null,
	val recipe: RecipeModel? = null,
	val isFav: Boolean = false
)