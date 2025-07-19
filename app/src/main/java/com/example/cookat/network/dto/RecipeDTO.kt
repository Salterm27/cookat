package com.example.cookat.network.dto

import com.google.gson.annotations.SerializedName

data class RecipeDto(
	val id: String,

	@SerializedName("user_id")
	val userID: String,
	val username: String?,
	val title: String,
	val description: String,
	val instructions: String,
	@SerializedName("image_url")
	val imageUrl: String?,
	val portions: Int,
	@SerializedName("is_approved")
	val isApproved: Boolean,
	@SerializedName("created_at")
	val createdAt: String?,
	val type: String?,
	@SerializedName("edited_date")
	val editedDate: String?,
	val rating: Double,
	@SerializedName("recipe_ingredients")
	val ingredientsJson: String?,
	val stepsJson: String?
)