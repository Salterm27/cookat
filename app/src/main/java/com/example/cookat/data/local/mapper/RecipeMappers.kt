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
	isFavourite = isFavourite,
	ingredients = ingredients,
	steps = steps

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
	isFavourite = isFavourite,
	ingredients = ingredients,
	steps = steps
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
	isFavourite = isFavourite,
	ingredients = ingredients,
	steps = steps
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
	isFavourite = isFavourite,
	ingredients = ingredients,
	steps = steps
)