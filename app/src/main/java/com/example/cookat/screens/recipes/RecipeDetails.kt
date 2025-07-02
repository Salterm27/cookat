package com.example.cookat.screens.recipes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetails(
	id: String,
	navController: NavController
) {
	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text("Detalle de Receta") },
			)
		}
	) { padding ->
		Column(
			modifier = Modifier
				.padding(padding)
				.padding(24.dp)
				.fillMaxSize(),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center
		) {
			Text(text = "ID de la receta: $id", style = MaterialTheme.typography.titleMedium)
			Spacer(modifier = Modifier.height(12.dp))
			Button(onClick = { navController.popBackStack() }) {
				Text("Volver al inicio")
			}
		}
	}
}
