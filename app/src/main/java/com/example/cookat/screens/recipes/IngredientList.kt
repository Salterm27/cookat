package com.example.cookat.screens.recipes

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cookat.models.dbModels.recipes.IngredientModel

fun LazyListScope.ingredientList(ingredients: List<IngredientModel>?) {
	item {
		Text(
			"Ingredientes",
			style = MaterialTheme.typography.headlineSmall,
			modifier = Modifier.padding(top = 16.dp)
		)
	}

	if (ingredients.isNullOrEmpty()) {
		item {
			Text("No data", style = MaterialTheme.typography.bodyMedium)
		}
	} else {
		items(ingredients) { ingredient ->
			Text(
				text = "- ${ingredient.quantity} ${ingredient.unit} de ${ingredient.name}",
				style = MaterialTheme.typography.bodyMedium,
				modifier = Modifier.padding(vertical = 2.dp)
			)
		}
	}
}