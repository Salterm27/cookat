package com.example.cookat.screens.recipes.editor.wizardScreens.component.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StepItemRow(
	number: Int,
	text: String,
	canMoveUp: Boolean,
	canMoveDown: Boolean,
	onMoveUp: () -> Unit,
	onMoveDown: () -> Unit,
	onEdit: () -> Unit,
	onDelete: () -> Unit,
	modifier: Modifier = Modifier
) {
	Row(
		modifier = modifier
			.fillMaxWidth()
			.padding(vertical = 4.dp),
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		Text("$number. $text", modifier = Modifier.weight(1f))
		if (canMoveUp) {
			TextButton(onClick = onMoveUp) { Text("↑") }
		} else {
			Spacer(Modifier.width(32.dp))
		}
		if (canMoveDown) {
			TextButton(onClick = onMoveDown) { Text("↓") }
		} else {
			Spacer(Modifier.width(32.dp))
		}
		TextButton(onClick = onEdit) { Text("Editar") }
		TextButton(onClick = onDelete) { Text("Eliminar") }
	}
}