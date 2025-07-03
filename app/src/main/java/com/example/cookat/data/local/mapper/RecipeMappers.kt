package com.example.cookat.data.local.mapper

import com.example.cookat.data.local.entities.RecipeEntity
import com.example.cookat.models.dbModels.recipes.RecipeModel
import com.example.cookat.network.dto.RecipeDto

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
	editedDate = editedDate
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
	editedDate = editedDate
)

fun RecipeDto.toEntity() = RecipeEntity(
	id = id,
	userID = userID,
	username = username,
	title = title,
	description = description,
	// map instructions if you store them
	portions = portions,
	isApproved = isApproved,
	createdAt = createdAt,
	type = type,
	editedDate = editedDate
)

fun RecipeDto.toModel() = RecipeModel(
	id = id,
	userID = userID,
	username = username,
	title = title,
	description = description,
	portions = portions,
	isApproved = isApproved,
	createdAt = createdAt,
	type = type,
	editedDate = editedDate
)