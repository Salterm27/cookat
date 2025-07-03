package com.example.cookat.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
	@PrimaryKey val id: String,
	val userID: String,
	val username: String?,
	val title: String,
	val description: String,
	val portions: Int,
	val isApproved: Boolean,
	val createdAt: String?,
	val type: String?,
	val editedDate: String?,
	val rating: Double
)