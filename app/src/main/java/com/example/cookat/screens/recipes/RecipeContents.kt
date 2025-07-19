package com.example.cookat.screens.recipes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cookat.R
import com.example.cookat.models.dbModels.recipes.RecipeModel

@Composable
fun RecipeContent(
	recipe: RecipeModel,
	modifier: Modifier = Modifier
) {
	LazyColumn(
		contentPadding = PaddingValues(16.dp),
		modifier = modifier
	) {
		item {
			Image(
				painter = painterResource(R.drawable.ic_recipe_placeholder),
				contentDescription = null,
				contentScale = ContentScale.Crop,
				modifier = Modifier
					.fillMaxWidth()
					.height(200.dp)
			)
			Text(
				"Por ${recipe.username}",
				style = MaterialTheme.typography.labelSmall,
				modifier = Modifier.padding(top = 8.dp)
			)
			Text(
				"Para ${recipe.portions} porciones",
				style = MaterialTheme.typography.titleMedium
			)
		}

		item {
			Text(
				"Ingredientes",
				style = MaterialTheme.typography.headlineSmall,
				modifier = Modifier.padding(top = 16.dp)
			)
			Text(
				text = recipe.ingredientsJson ?: "No data",
				style = MaterialTheme.typography.bodyMedium
			)
		}

		item {
			Text(
				"Pasos",
				style = MaterialTheme.typography.headlineSmall,
				modifier = Modifier.padding(top = 16.dp)
			)
			Text(
				text = recipe.stepsJson ?: "No data",
				style = MaterialTheme.typography.bodyMedium
			)
		}
	}
}