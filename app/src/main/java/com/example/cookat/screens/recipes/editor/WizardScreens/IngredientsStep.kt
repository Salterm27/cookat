package com.example.cookat.screens.recipes.editor.WizardScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cookat.models.uiStates.Ingredient
import com.example.cookat.models.uiStates.UnitOfMeasure
import com.example.cookat.screens.recipes.editor.WizardScreens.Component.IngredientDisplayRow
import com.example.cookat.screens.recipes.editor.WizardScreens.Component.Input.IngredientInputRow

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
		topBar = { /* ... */ },
		bottomBar = { /* ... */ }
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
			LazyColumn(
				modifier = Modifier
					.weight(1f)
					.fillMaxWidth(),
				contentPadding = PaddingValues(bottom = 60.dp)
			) {
				itemsIndexed(ingredients) { idx, ingredient ->
					IngredientDisplayRow(
						ingredient = ingredient,
						onRemove = { onRemoveIngredient(idx) }
					)
				}
			}
		}
	}
}