package com.example.cookat.models.dbModels

import java.util.Date

data class RecipeModel(
	val id: String = "",
	val userID: String = "",
	val title: String = "",
	val description: String = "",
	val type: String = "",
	val portions: Int = 0,
	val isApproved: Boolean = false,
	val createdAt: Date? = null,
	val editedDate: Date? = null
)