package com.example.cookat.viewmodels.recipes

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookat.data.local.session.SessionManager
import com.example.cookat.models.dbModels.recipes.IngredientModel
import com.example.cookat.models.dbModels.recipes.RecipeModel
import com.example.cookat.models.dbModels.recipes.StepModel
import com.example.cookat.models.uiStates.RecipeEditorUiState
import com.example.cookat.network.BackendClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipePublishViewModel(context: Context) : ViewModel() {

	private val api = BackendClient.create(context)
	private val sessionManager = SessionManager(context)

	private val _status = MutableStateFlow<RecipePostStatus>(RecipePostStatus.Idle)
	val status: StateFlow<RecipePostStatus> = _status

	fun publishRecipe(uiState: RecipeEditorUiState, isDraft: Boolean) {
		viewModelScope.launch {
			try {
				_status.value = RecipePostStatus.Loading

				val user = api.getCurrentUser()
				val recipe = buildRecipeModel(uiState, user.id, user.username, if (isDraft) "Draft" else "Review")

				api.createRecipe(recipe)

				_status.value = RecipePostStatus.Success
			} catch (e: Exception) {
				_status.value = RecipePostStatus.Error(e.message ?: "Error desconocido")
			}
		}
	}

	private fun buildRecipeModel(
		uiState: RecipeEditorUiState,
		userId: String,
		username: String?,
		state: String
	): RecipeModel {
		return RecipeModel(
			title = uiState.description.take(50), // o usar un campo real si lo tenés
			description = uiState.description,
			userID = userId,
			username = username,
			portions = uiState.servings,
			ingredients = uiState.ingredients.map {
				IngredientModel(
					name = it.name,
					quantity = it.quantity,
					unit = it.unit?.name
				)
			},
			steps = uiState.steps.mapIndexed { idx, step ->
				StepModel(
					stepNumber = idx + 1, description = step,
					id = TODO(),
					imageUrl = TODO()
				)
			},
			//state = state
		)
	}
}

sealed class RecipePostStatus {
	object Idle : RecipePostStatus()
	object Loading : RecipePostStatus()
	object Success : RecipePostStatus()
	data class Error(val message: String) : RecipePostStatus()
}
