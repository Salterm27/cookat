package com.example.cookat.screens.recipes.editor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cookat.models.uiStates.EditorStep
import com.example.cookat.screens.recipes.editor.wizardScreens.component.DescriptionStep
import com.example.cookat.screens.recipes.editor.wizardScreens.component.IngredientsStep
import com.example.cookat.screens.recipes.editor.wizardScreens.component.ServingsStep
import com.example.cookat.screens.recipes.editor.wizardScreens.component.StepsStep
import com.example.cookat.screens.recipes.preview.RecipePreview
import com.example.cookat.viewmodels.recipes.RecipeEditorViewModel

@Composable
fun RecipeEditor(
	recipeName: String,
	onCancel: () -> Unit,
	onFinish: () -> Unit,
	viewModel: RecipeEditorViewModel = viewModel()
) {
	val state by viewModel.uiState.collectAsState()
	LaunchedEffect(Unit) {
		viewModel.initialize(recipeName)
	}
	when (state.currentStep) {
		EditorStep.Description -> DescriptionStep(
			recipeName = recipeName,
			description = state.description,
			onDescriptionChange = { viewModel.setDescription(it) },
			onNext = { viewModel.nextStep() },
			onCancel = onCancel
		)

		EditorStep.Ingredients -> IngredientsStep(
			ingredients = state.ingredients,
			onAddIngredient = { name, qty, unit -> viewModel.addIngredient(name, qty, unit) },
			onRemoveIngredient = { idx -> viewModel.removeIngredient(idx) },
			onNext = { viewModel.nextStep() },
			onBack = { viewModel.prevStep() }
		)

		EditorStep.Servings -> ServingsStep(
			servings = state.servings,
			onServingsChange = { viewModel.setServings(it) },
			onNext = { viewModel.nextStep() },
			onBack = { viewModel.prevStep() }
		)

		EditorStep.Steps -> StepsStep(
			steps = state.steps,
			onAddStep = { viewModel.addStep(it) },
			onRemoveStep = { viewModel.removeStep(it) },
			onEditStep = { idx, newStep -> viewModel.editStep(idx, newStep) }, // Pass this!
			onMoveStep = { from, to -> viewModel.moveStep(from, to) },         // Pass this!
			onNext = { viewModel.nextStep() },
			onBack = { viewModel.prevStep() }
		)

		EditorStep.Preview -> RecipePreview(
			state = state,
			viewModel = viewModel,
			onEditStep = { viewModel.goToStep(it) },
			onSaved = onFinish,
			onBack = { viewModel.prevStep() }
		)
	}
}