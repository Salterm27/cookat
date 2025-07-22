package com.example.cookat.screens.recipes.editor.wizardScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cookat.models.uiStates.Ingredient
import com.example.cookat.models.uiStates.UnitOfMeasure
import com.example.cookat.screens.recipes.editor.wizardScreens.component.ingredients.IngredientList
import com.example.cookat.screens.recipes.editor.wizardScreens.component.input.IngredientInputRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientsStep(
	ingredients: List<Ingredient>,
	onAddIngredient: (String, Double, UnitOfMeasure) -> Unit,
	onRemoveIngredient: (Int) -> Unit,
	onNext: () -> Unit,
	onBack: () -> Unit
) {
	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text("Paso 1 - Contanos sobre tu receta") },
				navigationIcon = {
					TextButton(onClick = onBack) {
						Text("Volver")
					}
				}
			)
		},
		bottomBar = {
			Button(
				onClick = onNext,
				enabled = ingredients.isNotEmpty(),
				modifier = Modifier.fillMaxWidth()
			) {
				Text("Siguiente")
			}
		}
	) { padding ->
		Column(
			modifier = Modifier
				.padding(padding)
				.fillMaxSize()
				.padding(horizontal = 16.dp, vertical = 8.dp)
		) {
			// Top: inputs and add button
			IngredientInputRow(onAdd = onAddIngredient)
			Spacer(Modifier.height(24.dp))
			Text("Lista de ingredientes:", style = MaterialTheme.typography.labelLarge)
			Spacer(Modifier.height(8.dp))
			// Scrollable ingredient list
			IngredientList(
				ingredients = ingredients,
				onRemoveIngredient = onRemoveIngredient,
				modifier = Modifier.weight(1f)
			)
		}
	}
}