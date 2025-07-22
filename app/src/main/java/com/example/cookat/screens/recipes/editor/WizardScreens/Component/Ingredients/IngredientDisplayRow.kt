package com.example.cookat.screens.recipes.editor.WizardScreens.Component.Ingredients

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cookat.models.uiStates.Ingredient

@Composable
fun IngredientDisplayRow(
	ingredient: Ingredient,
	onRemove: () -> Unit,
	modifier: Modifier = Modifier
) {
	Row(
		modifier = modifier
			.fillMaxWidth()
			.padding(vertical = 6.dp),
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		Text(
			"${ingredient.quantity} ${ingredient.unit?.display.orEmpty()} ${ingredient.name}",
			style = MaterialTheme.typography.bodyLarge,
			modifier = Modifier.weight(1f)
		)
		TextButton(
			onClick = onRemove
		) { Text("Eliminar") }
	}
}