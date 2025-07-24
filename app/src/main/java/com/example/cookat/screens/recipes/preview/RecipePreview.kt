package com.example.cookat.screens.recipes.preview

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cookat.models.uiStates.EditorStep
import com.example.cookat.models.uiStates.RecipeEditorUiState
import com.example.cookat.viewmodels.recipes.RecipeEditorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipePreview(
	state: RecipeEditorUiState,
	viewModel: RecipeEditorViewModel,
	onEditStep: (EditorStep) -> Unit,
	onSaved: () -> Unit,
	onBack:() -> Unit
) {
	val context = LocalContext.current
	var feedback by remember { mutableStateOf<String?>(null) }

	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text("Vista previa") },
				navigationIcon = { TextButton(onClick = onBack) { Text("Volver") } }
			)
		},
		bottomBar = {
			Row(
				Modifier
					.fillMaxWidth()
					.padding(16.dp),
				horizontalArrangement = Arrangement.SpaceEvenly
			) {
				Button(
					onClick = {
						viewModel.submitRecipe(context, "Draft") { success, error ->
							feedback = if (success) {
								onSaved()
								"Receta guardada como borrador"
							} else {
								error ?: "Hubo un error al guardar"
							}
						}
					},
					colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
				) { Text("Guardar borrador") }

				Button(
					onClick = {
						viewModel.submitRecipe(context, "Review") { success, error ->
							feedback = if (success) {
								onSaved()
								"¡Receta enviada a revisión!"
							} else {
								error ?: "No se pudo enviar a revisión"
							}
						}
					}
				) { Text("Publicar") }
			}
		}
	) { padding ->
		Column(
			modifier = Modifier
				.padding(padding)
				.padding(horizontal = 16.dp, vertical = 8.dp)
		) {
			feedback?.let {
				Text(
					text = it,
					color = MaterialTheme.colorScheme.primary,
					modifier = Modifier.padding(vertical = 8.dp)
				)
			}
			// Title & Description
			SectionHeader(title = "Título y descripción", onEdit = { onEditStep(EditorStep.Description) })
			Text(text = state.name, style = MaterialTheme.typography.titleMedium)
			Text(state.description, style = MaterialTheme.typography.bodyMedium)
			Spacer(Modifier.height(20.dp))

			// Ingredients
			SectionHeader(title = "Ingredientes", onEdit = { onEditStep(EditorStep.Ingredients) })
			state.ingredients.forEach { ing ->
				Text("- ${ing.quantity} ${ing.unit?.display.orEmpty()} ${ing.name}")
			}
			Spacer(Modifier.height(20.dp))

			// Servings
			SectionHeader(title = "Porciones", onEdit = { onEditStep(EditorStep.Servings) })
			Text("${state.servings}")
			Spacer(Modifier.height(20.dp))

			// Steps
			SectionHeader(title = "Preparación", onEdit = { onEditStep(EditorStep.Steps) })
			state.steps.forEachIndexed { idx, step ->
				Text("${idx + 1}. $step")
			}
		}
	}
}

@Composable
fun SectionHeader(title: String, onEdit: () -> Unit) {
	Row(
		modifier = Modifier.fillMaxWidth(),
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		Text(title, style = MaterialTheme.typography.titleMedium)
		TextButton(onClick = onEdit) { Text("Editar") }
	}
	Spacer(Modifier.height(4.dp))
}