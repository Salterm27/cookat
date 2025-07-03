package com.example.cookat.screens.recipes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cookat.R
import com.example.cookat.repository.RecipeRepository
import com.example.cookat.viewmodels.recipes.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetails(
	recipeId: String,
	navController: NavController
) {
	val context = LocalContext.current
	val repository = RecipeRepository(context)

	val viewModel: RecipeViewModel = viewModel(
		factory = object : ViewModelProvider.Factory {
			override fun <T : ViewModel> create(modelClass: Class<T>): T {
				if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
					@Suppress("UNCHECKED_CAST")
					return RecipeViewModel(repository, recipeId) as T
				}
				throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
			}
		}
	)

	val state = viewModel.uiState.collectAsState().value

	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text(state.recipe?.title ?: "") },
				navigationIcon = {
					IconButton(onClick = { navController.navigateUp() }) {
						Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
					}
				}
			)
		},
		floatingActionButton = {
			Column(
				verticalArrangement = Arrangement.spacedBy(12.dp),
				horizontalAlignment = Alignment.End,
				modifier = Modifier.padding(bottom = 24.dp, end = 16.dp)
			) {
				FloatingActionButton(
					onClick = { viewModel.toggleFavorite() },
					shape = MaterialTheme.shapes.medium
				) {
					Icon(
						imageVector = Icons.Filled.FavoriteBorder, // Replace with isFavorite logic
						contentDescription = "Favorite"
					)
				}
				FloatingActionButton(onClick = { /* TODO */ }) {
					Icon(Icons.Filled.MoreVert, contentDescription = "Actions")
				}
				FloatingActionButton(onClick = { /* TODO */ }) {
					Icon(Icons.AutoMirrored.Filled.Comment, contentDescription = "Comment")
				}
			}
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
				val recipe = state.recipe
				LazyColumn(
					contentPadding = padding
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
						Text("Por ${recipe.username}", modifier = Modifier.padding(16.dp))
						Text("Porciones: ${recipe.portions}", modifier = Modifier.padding(16.dp))
						Text("Ingredientes", modifier = Modifier.padding(16.dp))
					}
					//TODO: Add items for your ingredients + steps...
				}
			}
		}
	}
}