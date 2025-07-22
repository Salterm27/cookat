package com.example.cookat.screens.recipes.editor.wizardScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DescriptionStep(
	recipeName: String,
	description: String,
	onDescriptionChange: (String) -> Unit,
	onNext: () -> Unit,
	onCancel: () -> Unit
) {
	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text("Paso 1 - Contanos sobre tu receta") },
				navigationIcon = {
					TextButton(onClick = onCancel) {
						Text("Cancelar")
					}
				}
			)
		}
	) { padding ->
		Column(
			modifier = Modifier
				.padding(padding)
				.fillMaxSize()
				.padding(24.dp),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Text(
				"Hoy cocinamos: $recipeName",
				style = MaterialTheme.typography.titleMedium
			)
			Spacer(Modifier.height(20.dp))
			OutlinedTextField(
				value = description,
				onValueChange = onDescriptionChange,
				label = { Text("Descripción corta") },
				singleLine = false,
				modifier = Modifier.fillMaxWidth()
			)
			Spacer(Modifier.height(24.dp))
			Button(
				onClick = onNext,
				enabled = description.isNotBlank(),
				modifier = Modifier.fillMaxWidth()
			) {
				Text("Siguiente")
			}
		}
	}
}