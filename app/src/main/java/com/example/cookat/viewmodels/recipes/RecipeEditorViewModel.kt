package com.example.cookat.viewmodels.recipes

import androidx.lifecycle.ViewModel
import com.example.cookat.models.uiStates.EditorStep
import com.example.cookat.models.uiStates.Ingredient
import com.example.cookat.models.uiStates.RecipeEditorUiState
import com.example.cookat.models.uiStates.UnitOfMeasure
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


class RecipeEditorViewModel : ViewModel() {
	private val _uiState = MutableStateFlow(RecipeEditorUiState())
	val uiState: StateFlow<RecipeEditorUiState> = _uiState

	fun setDescription(desc: String) {
		_uiState.update { it.copy(description = desc) }
	}

	fun addIngredient(name: String, quantity: Double, unit: UnitOfMeasure?) {
		if (name.isBlank()) return
		_uiState.update { it.copy(ingredients = it.ingredients + Ingredient(name, quantity, unit)) }
	}

	fun removeIngredient(idx: Int) {
		_uiState.update { it.copy(ingredients = it.ingredients.filterIndexed { i, _ -> i != idx }) }
	}

	fun setServings(servings: Int) {
		_uiState.update { it.copy(servings = servings) }
	}

	fun addStep(step: String) {
		if (step.isNotBlank())
			_uiState.update { it.copy(steps = it.steps + step) }
	}

	fun removeStep(idx: Int) {
		_uiState.update { it.copy(steps = it.steps.filterIndexed { i, _ -> i != idx }) }
	}

	fun nextStep() {
		_uiState.update { it.copy(currentStep = EditorStep.values()[it.currentStep.ordinal + 1]) }
	}

	fun prevStep() {
		_uiState.update { it.copy(currentStep = EditorStep.values()[it.currentStep.ordinal - 1]) }
	}

	fun goTo(step: EditorStep) {
		_uiState.update { it.copy(currentStep = step) }
	}
}