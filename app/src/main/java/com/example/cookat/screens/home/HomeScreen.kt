package com.example.cookat.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cookat.models.dbModels.recipes.RecipeModel
import com.example.cookat.screens.home.components.RecipeCard
import com.example.cookat.screens.home.components.RecipeCardData
import com.example.cookat.screens.home.sidemenu.DrawerContent
import com.example.cookat.viewmodels.home.HomeViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
	val context = LocalContext.current

	val viewModel: HomeViewModel = viewModel(factory = object : ViewModelProvider.Factory {
		override fun <T : ViewModel> create(modelClass: Class<T>): T {
			return HomeViewModel(context.applicationContext) as T
		}
	})

	val drawerState = rememberDrawerState(DrawerValue.Closed)
	val scope = rememberCoroutineScope()
	val state = viewModel.uiState

	ModalNavigationDrawer(
		drawerState = drawerState,
		drawerContent = { DrawerContent(navController) }
	) {
		Scaffold(
			topBar = {
				TopAppBar(
					title = { Text("Home") },
					navigationIcon = {
						IconButton(onClick = { scope.launch { drawerState.open() } }) {
							Icon(Icons.Filled.Menu, contentDescription = "Open Menu")
						}
					},
					actions = {
						IconButton(onClick = { /* Search */ }) {
							Icon(Icons.Filled.Search, contentDescription = "Search")
						}
						IconButton(onClick = { /* Favorites */ }) {
							Icon(Icons.Filled.FavoriteBorder, contentDescription = "Favorites")
						}
					}
				)
			}
		) { padding ->
			Column(
				modifier = Modifier
					.padding(padding)
					.padding(16.dp)
					.fillMaxSize()
			) {
				when {
					state.isLoading -> {
						Text("Loading...")
					}
					state.errorMessage != null -> {
						Text("Error: ${state.errorMessage}")
					}
					else -> {
						val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

						state.recipes.forEach { recipeModel: RecipeModel ->
							val updatedAt = recipeModel.editedDate?.let {
								try {
									formatter.parse(it)
								} catch (e: Exception) {
									null
								}
							} ?: Date()

							val recipeCard = RecipeCardData(
								id = recipeModel.id,
								title = recipeModel.title,
								author = "Autor desconocido", // actualizar cuando tengas el nombre real
								vote = 4.5f,
								isFavorite = false,
								imageUrl = null,
								updatedAt = updatedAt
							)

							RecipeCard(
								recipe = recipeCard,
								onClick = {
									navController.navigate("recipe/${recipeCard.id}")
								},
								onToggleFavorite = { isFav ->
									println("Receta ${recipeCard.id} marcada como favorita: $isFav")
								}
							)
						}
					}
				}
			}
		}
	}
}
