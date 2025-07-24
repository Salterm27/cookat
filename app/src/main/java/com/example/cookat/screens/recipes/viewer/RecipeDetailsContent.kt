package com.example.cookat.screens.recipes.viewer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.cookat.models.uiStates.RecipeUiState
import com.example.cookat.screens.recipes.viewer.components.RecipeActions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsContent(
	state: RecipeUiState,
	onBack: () -> Unit,
	onToggleFavorite: () -> Unit,
) {
	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text(state.recipe?.title ?: "") },
				navigationIcon = {
					IconButton(onClick = onBack) {
						Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
					}
				}
			)
		},
		floatingActionButton = {
			RecipeActions(
				state = state,
				onToggleFavorite = onToggleFavorite
			)
		}
	) { padding ->
		when {
			state.isLoading -> {
				Box(
					modifier = Modifier
						.fillMaxSize()
						.padding(padding),
					contentAlignment = Alignment.Center
				) {
					CircularProgressIndicator()
				}
			}

			state.errorMessage != null -> {
				Text(
					text = "Error: ${state.errorMessage}",
					modifier = Modifier.padding(padding)
				)
			}

			state.recipe != null -> {
				RecipeViewer(
					recipe = state.recipe,
					modifier = Modifier.padding(padding)
				)
			}
		}
	}
}