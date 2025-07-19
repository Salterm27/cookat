package com.example.cookat.models.dbModels.recipes

data class IngredientModel(
	val ingredientId: String? = "",
	val quantity: Double = 0.0,
	val name: String? = "",
	val unit: String? = ""
)