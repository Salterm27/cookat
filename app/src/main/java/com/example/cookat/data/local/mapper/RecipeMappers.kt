package com.example.cookat.data.local.mapper

import com.example.cookat.data.local.entities.RecipeEntity
import com.example.cookat.models.dbModels.recipes.RecipeModel

fun RecipeEntity.toModel() = RecipeModel(
	id = id,
	userID = userID,
	title = title,
	description = description,
	portions = portions,
	isApproved = isApproved,
	createdAt = createdAt,
	type = type,
	editedDate = editedDate
)

fun RecipeModel.toEntity() = RecipeEntity(
	id = id,
	userID = userID,
	title = title,
	description = description,
	portions = portions,
	isApproved = isApproved,
	createdAt = createdAt,
	type = type,
	editedDate = editedDate
)