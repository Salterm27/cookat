package com.example.cookat.screens.home.components.dialogs

import NewRecipeNameDialog
import RecipeExistsDialog
import androidx.compose.runtime.Composable
import com.example.cookat.models.uiStates.HomeUiState
import com.example.cookat.viewmodels.home.HomeViewModel

@Composable
fun HomeDialogs(
	state: HomeUiState,
	viewModel: HomeViewModel
) {
	if (state.showNewRecipeDialog) {
		NewRecipeNameDialog(
			name = state.pendingRecipeName,
			error = state.recipeNameError,
			isChecking = state.isCheckingRecipeName,
			onNameChange = { viewModel.onRecipeNameChange(it) },
			onDismiss = { viewModel.hideNewRecipeDialog() },
			onSubmit = { viewModel.submitNewRecipeName() }
		)
	}

	if (state.showNameExistsDialog) {
		RecipeExistsDialog(
			name = state.pendingRecipeName,
			onNameChange = { viewModel.onRecipeNameChange(it) },
			onReplace = { viewModel.onReplaceRecipe() },
			onModify = { viewModel.onModifyRecipe() },
			onTryAgain = { viewModel.submitNewRecipeName() },
			onDismiss = { viewModel.hideNameExistsDialog() }
		)
	}
}