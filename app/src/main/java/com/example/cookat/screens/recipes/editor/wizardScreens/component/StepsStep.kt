package com.example.cookat.screens.recipes.editor.wizardScreens.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cookat.screens.recipes.editor.wizardScreens.component.steps.StepItemRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StepsStep(
	steps: List<String>,
	onAddStep: (String) -> Unit,
	onRemoveStep: (Int) -> Unit,
	onEditStep: (Int, String) -> Unit,
	onMoveStep: (from: Int, to: Int) -> Unit,
	onNext: () -> Unit,
	onBack: () -> Unit
) {
	var stepText by remember { mutableStateOf("") }
	var editingIndex by remember { mutableStateOf<Int?>(null) }

	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text("Paso 3 - ¿Cómo se prepara?") },
				navigationIcon = { TextButton(onClick = onBack) { Text("Volver") } }
			)
		},
		bottomBar = {
			Button(
				onClick = onNext,
				enabled = steps.isNotEmpty(),
				modifier = Modifier
					.fillMaxWidth()
					.padding(16.dp)
			) { Text("Finalizar") }
		}
	) { padding ->
		Column(
			modifier = Modifier
				.padding(padding)
				.fillMaxSize()
				.padding(24.dp)
		) {
			Text("Escribe los pasos de preparación:", style = MaterialTheme.typography.titleLarge)
			Spacer(Modifier.height(12.dp))

			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.spacedBy(8.dp)
			) {
				OutlinedTextField(
					value = stepText,
					onValueChange = { stepText = it },
					label = { Text(if (editingIndex == null) "Nuevo paso" else "Editar paso") },
					modifier = Modifier.weight(1f)
				)
				if (editingIndex == null) {
					Button(
						onClick = {
							onAddStep(stepText.trim())
							stepText = ""
						},
						enabled = stepText.isNotBlank(),
						modifier = Modifier.alignByBaseline()
					) { Text("+") }
				} else {
					Button(
						onClick = {
							onEditStep(editingIndex!!, stepText.trim())
							editingIndex = null
							stepText = ""
						},
						enabled = stepText.isNotBlank(),
						modifier = Modifier.alignByBaseline()
					) { Text("Actualizar") }
					TextButton(
						onClick = {
							editingIndex = null
							stepText = ""
						},
						modifier = Modifier.alignByBaseline()
					) { Text("Cancelar") }
				}
			}

			Spacer(Modifier.height(16.dp))

			Text("Pasos agregados:", style = MaterialTheme.typography.labelLarge)
			Spacer(Modifier.height(8.dp))

			LazyColumn(
				modifier = Modifier
					.weight(1f)
					.fillMaxWidth()
			) {
				itemsIndexed(steps) { idx, step ->
					StepItemRow(
						number = idx + 1,
						text = step,
						canMoveUp = idx > 0,
						canMoveDown = idx < steps.lastIndex,
						onMoveUp = { onMoveStep(idx, idx - 1) },
						onMoveDown = { onMoveStep(idx, idx + 1) },
						onEdit = {
							editingIndex = idx
							stepText = step
						},
						onDelete = { onRemoveStep(idx) }
					)
				}
			}
		}
	}
}
