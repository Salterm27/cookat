package com.example.cookat.data.local.mapper

import android.R.attr.rating
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
	editedDate = editedDate,
	rating = rating
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
	rating = rating
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
	editedDate = editedDate,
	rating = rating
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
	editedDate = editedDate,
	rating = rating

)