package com.example.cookat.screens.recipes.preview

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.cookat.models.uiStates.EditorStep
import com.example.cookat.models.uiStates.RecipeEditorUiState
import com.example.cookat.viewmodels.recipes.RecipePostStatus
import com.example.cookat.viewmodels.recipes.RecipePublishViewModel

@Composable
fun RecipePreviewScreen(
	state: RecipeEditorUiState,
	onBack: () -> Unit,
	onEditStep: (EditorStep) -> Unit,
	onSuccess: () -> Unit
) {
	val context = LocalContext.current
	val viewModel = remember { RecipePublishViewModel(context) }
	val status by viewModel.status.collectAsState()

	// Feedback visual según estado
	when (status) {
		is RecipePostStatus.Loading -> {
			// Mostrar loading si querés
			Text("Cargando...")
		}
		is RecipePostStatus.Success -> {
			// Llamar callback para navegar, limpiar, etc.
			onSuccess()
		}
		is RecipePostStatus.Error -> {
			val msg = (status as RecipePostStatus.Error).message
			Text("Error: $msg") // Podés mostrar Snackbar, Dialog o Toast
		}
		else -> Unit
	}

	// Mostramos el componente visual
	RecipePreview(
		state = state,
		onEditStep = onEditStep,
		onSaveDraft = {
			viewModel.publishRecipe(state, isDraft = true)
		},
		onPublish = {
			viewModel.publishRecipe(state, isDraft = false)
		},
		onBack = onBack
	)
}
