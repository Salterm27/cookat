package com.example.cookat.screens.recipes.editor.WizardScreens.Component.Input

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cookat.models.uiStates.UnitOfMeasure

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientInputRow(
	onAdd: (String, Double, UnitOfMeasure) -> Unit,
	modifier: Modifier = Modifier
) {
	var name by remember { mutableStateOf("") }
	var qty by remember { mutableStateOf("") }
	var selectedUnit by remember { mutableStateOf<UnitOfMeasure?>(null) }
	var unitMenuExpanded by remember { mutableStateOf(false) }

	Column(modifier) {
		OutlinedTextField(
			value = qty,
			onValueChange = { qty = it },
			label = { Text("Cantidad") },
			modifier = Modifier.fillMaxWidth()
		)
		Spacer(Modifier.height(8.dp))
		ExposedDropdownMenuBox(
			expanded = unitMenuExpanded,
			onExpandedChange = { unitMenuExpanded = !unitMenuExpanded }
		) {
			OutlinedTextField(
				readOnly = true,
				value = selectedUnit?.display ?: "",
				onValueChange = {},
				label = { Text("Unidad") },
				trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(unitMenuExpanded) },
				modifier = Modifier
					.menuAnchor()
					.fillMaxWidth()
			)
			ExposedDropdownMenu(
				expanded = unitMenuExpanded,
				onDismissRequest = { unitMenuExpanded = false }
			) {
				UnitOfMeasure.values().forEach { unit ->
					DropdownMenuItem(
						text = { Text(unit.display) },
						onClick = {
							selectedUnit = unit
							unitMenuExpanded = false
						}
					)
				}
			}
		}
		Spacer(Modifier.height(8.dp))
		OutlinedTextField(
			value = name,
			onValueChange = { name = it },
			label = { Text("Ingrediente") },
			modifier = Modifier.fillMaxWidth()
		)
		Spacer(Modifier.height(8.dp))
		Button(
			onClick = {
				val quantity = qty.toDoubleOrNull()
				if (name.isNotBlank() && quantity != null && selectedUnit != null) {
					onAdd(name.trim(), quantity, selectedUnit!!)
					name = ""
					qty = ""
					selectedUnit = null
				}
			},
			enabled = name.isNotBlank() && qty.toDoubleOrNull() != null && selectedUnit != null,
			modifier = Modifier.fillMaxWidth()
		) { Text("+ Agregar ingrediente") }
	}
}