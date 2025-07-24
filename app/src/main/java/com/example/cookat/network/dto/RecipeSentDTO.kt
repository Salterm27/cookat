package com.example.cookat.network.dto

import com.example.cookat.models.dbModels.recipes.IngredientModel
import com.example.cookat.models.dbModels.recipes.StepModel
import com.google.gson.annotations.SerializedName

data class RecipeSentDto(
	val title: String,
	val description: String,
	val portions: Int,
	val type: String = "Salado",
	@SerializedName("recipe_ingredients")
	val ingredients: List<IngredientModel>?,
	val steps: List<StepModel>?,
	val state: String = "Draft"
)