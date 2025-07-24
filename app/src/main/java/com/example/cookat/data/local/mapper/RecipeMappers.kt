package com.example.cookat.data.local.mapper

import com.example.cookat.data.local.entities.RecipeEntity
import com.example.cookat.models.dbModels.recipes.IngredientModel
import com.example.cookat.models.dbModels.recipes.RecipeModel
import com.example.cookat.models.dbModels.recipes.StepModel
import com.example.cookat.models.uiStates.RecipeEditorUiState
import com.example.cookat.network.dto.RecipeReceivedDTO
import com.example.cookat.network.dto.RecipeSentDto

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
	steps = steps,
	state = state

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
	steps = steps,
	state = state

)

// === Received DTO -> Entity ===

fun RecipeReceivedDTO.toEntity() = RecipeEntity(
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
	isFavourite = isfav,
	ingredients = ingredients,
	steps = steps,
	state = state
)

// === Received DTO -> Model ===

fun RecipeReceivedDTO.toModel() = RecipeModel(
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
	isFavourite = isfav,
	ingredients = ingredients,
	steps = steps,
	state = state
)

// EditorUiState -> RecipeSentDto --
fun RecipeEditorUiState.toRecipeSentDto(
	type: String = "Salado",    // or pass this as a param
	state: String = "Draft"   // or "Review"
): RecipeSentDto {
	return RecipeSentDto(
		title = name,
		description = description,
		portions = servings,
		ingredients = ingredients.map {
			IngredientModel(
				name = it.name,
				quantity = it.quantity,
				unit = it.unit?.display ?: ""
			)
		},
		steps = steps.mapIndexed { idx, desc ->
			StepModel(
				stepNumber = idx + 1,
				description = desc,
				imageUrl = ""
			)
		},
		state = state,
		type = type
	)
}

