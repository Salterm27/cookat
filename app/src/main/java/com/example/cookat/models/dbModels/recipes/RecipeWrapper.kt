package com.example.cookat.models.dbModels.recipes

data class RecipesWrapper(
	val total: Int,
	val page: Int,
	val limit: Int,
	val results: List<RecipeModel>
)