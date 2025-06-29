package com.example.cookat.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "recipes")
data class RecipeEntity(
	@PrimaryKey val id: String,
	val userID: String,
	val title: String,
	val description: String,
	val portions: Int,
	val isApproved: Boolean,
	val createdAt: Date?,
	val type: String,
	val editedDate: Date?
	// You can skip nested lists (ingredients) for now or store them as JSON if needed.
)