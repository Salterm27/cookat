package com.example.cookat.models.dbModels.recipes

data class RecipeModel(
	val id: String = "",
	val userID: String = "",
	val title: String = "",
	val description: String = "",
	val type: String? = null,
	val portions: Int = 0,
	val isApproved: Boolean = false,
	val createdAt: String? = null,
	val editedDate: String? = null
)