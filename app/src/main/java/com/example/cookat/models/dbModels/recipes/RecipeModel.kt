package com.example.cookat.models.dbModels.recipes

data class RecipeModel(
	val id: String = "",
	val userID: String = "",
	val username: String? = null,
	val title: String = "",
	val description: String = "",
	val type: String? = null,
	val portions: Int = 0,
	val isApproved: Boolean = false,
	val createdAt: String? = null,
	val editedDate: String? = null,
	val rating: Double = 0.0,
	val isFavourite: Boolean = false,
	val ingredients: List<IngredientModel>? = null,
	val steps: List<StepModel>? = null
)