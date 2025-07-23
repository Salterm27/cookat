package com.example.cookat.screens.recipes.preview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cookat.models.uiStates.EditorStep
import com.example.cookat.models.uiStates.RecipeEditorUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipePreview(
	state: RecipeEditorUiState,
	onEditStep: (EditorStep) -> Unit,
	onSaveDraft: () -> Unit,
	onPublish: () -> Unit,
	onBack: () -> Unit
) {
	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text("Vista previa") },
				navigationIcon = { TextButton(onClick = onBack) { Text("Volver") } }
			)
		},
		bottomBar = {
			Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
				Button(onClick = onSaveDraft) { Text("Guardar borrador") }
				Button(onClick = onPublish, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) { Text("Publicar") }
			}
		}
	) { padding ->
		Column(
			modifier = Modifier
				.padding(padding)
				.padding(horizontal = 16.dp, vertical = 8.dp)
		) {
			// Title & Description
			SectionHeader(title = "Título y descripción", onEdit = { onEditStep(EditorStep.Description) })
			Text(state.description)
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
