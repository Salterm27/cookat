package com.example.cookat.data.local.mapper

import com.example.cookat.data.local.entities.RecipeEntity
import com.example.cookat.models.dbModels.recipes.RecipeModel
import com.example.cookat.network.dto.RecipeDto

// === Entity <-> Model ===

fun RecipeEntity.toModel() = RecipeModel(
	id = id,
	userID = userID,
	username = username,
	title = title,
	description = description,
	portions = portions,
	isApproved = isApproved,
	createdAt = createdAt,
	type = type,
	editedDate = editedDate,
	rating = rating,
	isFavourite = isFavourite
)

fun RecipeModel.toEntity() = RecipeEntity(
	id = id,
	userID = userID,
	username = username,
	title = title,
	description = description,
	portions = portions,
	isApproved = isApproved,
	createdAt = createdAt,
	type = type,
	editedDate = editedDate,
	rating = rating,
	isFavourite = isFavourite
)

// === DTO -> Entity ===

fun RecipeDto.toEntity(isFavourite: Boolean = false) = RecipeEntity(
	id = id,
	userID = userID,
	username = username,
	title = title,
	description = description,
	portions = portions,
	isApproved = isApproved,
	createdAt = createdAt,
	type = type,
	editedDate = editedDate,
	rating = rating,
	isFavourite = isFavourite
)

// === DTO -> Model ===

fun RecipeDto.toModel(isFavourite: Boolean = false) = RecipeModel(
	id = id,
	userID = userID,
	username = username,
	title = title,
	description = description,
	portions = portions,
	isApproved = isApproved,
	createdAt = createdAt,
	type = type,
	editedDate = editedDate,
	rating = rating,
	isFavourite = isFavourite
)