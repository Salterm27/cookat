package com.example.cookat.screens.recipes

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cookat.models.dbModels.recipes.StepModel

fun LazyListScope.stepList(steps: List<StepModel>?) {
	item {
		Text(
			"Pasos",
			style = MaterialTheme.typography.headlineSmall,
			modifier = Modifier.padding(top = 16.dp)
		)
	}

	if (steps.isNullOrEmpty()) {
		item {
			Text("No data", style = MaterialTheme.typography.bodyMedium)
		}
	} else {
		items(steps.sortedBy { it.stepNumber }) { step ->
			Text(
				text = "${step.stepNumber}. ${step.description}",
				style = MaterialTheme.typography.bodyMedium,
				modifier = Modifier.padding(vertical = 4.dp)
			)
		}
	}
}