package com.example.cookat.screens.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cookat.models.dbModels.recipes.RecipeModel
import com.example.cookat.models.uiStates.HomeUiState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeContent(
	modifier: Modifier = Modifier,
	state: HomeUiState,
	navController: NavController,
	onToggleFavorite: (String, Boolean) -> Unit
) {
	Column(
		modifier = modifier
			.padding(16.dp)
			.fillMaxSize()
	) {
		when {
			state.isLoading -> {
				Text("Loading...")
			}

			state.errorMessage != null -> {
				Text("Error: ${state.errorMessage}", color = MaterialTheme.colorScheme.error)
			}

			else -> {
				val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

				state.recipes.forEach { recipeModel: RecipeModel ->
					val editedDate = recipeModel.editedDate?.let {
						try {
							formatter.parse(it)
						} catch (e: Exception) {
							null
						}
					} ?: Date()

					RecipeCard(
						recipe = recipeModel,
						onClick = {
							navController.navigate("recipe/${recipeModel.id}")
						},
						onToggleFavorite = { isFav ->
							onToggleFavorite(recipeModel.id, isFav)
						}
					)
				}
			}
		}
	}
}