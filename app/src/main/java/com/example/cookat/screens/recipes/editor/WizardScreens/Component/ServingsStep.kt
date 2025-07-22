package com.example.cookat.screens.recipes.editor.WizardScreens.Component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
fun ServingsStep(
	servings: Int,
	onServingsChange: (Int) -> Unit,
	onNext: () -> Unit,
	onBack: () -> Unit
) {
	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text("Paso 3 - Las porciones") },
				navigationIcon = {
					TextButton(onClick = onBack) { Text("Volver") }
				}
			)
		},
		bottomBar = {
			Button(
				onClick = onNext,
				enabled = servings > 0,
				modifier = Modifier
					.fillMaxWidth()
					.padding(16.dp)
			) { Text("Siguiente") }
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
				"¿Para cuántas porciones es esta receta?",
				style = MaterialTheme.typography.titleLarge
			)
			Spacer(Modifier.height(16.dp))
			Text(
				"Esto ayuda a ajustar cantidades automáticamente.",
				style = MaterialTheme.typography.bodyMedium,
				modifier = Modifier.padding(bottom = 24.dp)
			)

			Row(verticalAlignment = Alignment.CenterVertically) {
				OutlinedButton(
					onClick = { if (servings > 1) onServingsChange(servings - 1) },
					enabled = servings > 1
				) { Text("-") }
				Spacer(Modifier.width(12.dp))
				OutlinedTextField(
					value = servings.toString(),
					onValueChange = {
						val newValue = it.toIntOrNull() ?: 1
						if (newValue > 0) onServingsChange(newValue)
					},
					label = { Text("Porciones") },
					singleLine = true,
					modifier = Modifier.width(100.dp)
				)
				Spacer(Modifier.width(12.dp))
				OutlinedButton(
					onClick = { onServingsChange(servings + 1) }
				) { Text("+") }
			}
		}
	}
}