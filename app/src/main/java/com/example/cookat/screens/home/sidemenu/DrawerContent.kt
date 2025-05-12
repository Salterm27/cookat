package com.example.cookat.screens.home.sidemenu


import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight

@Composable
fun DrawerContent() {
	Surface(
		color = MaterialTheme.colorScheme.surfaceVariant,
		tonalElevation = 4.dp,
		shadowElevation = 8.dp,
		shape = MaterialTheme.shapes.medium,
		modifier = Modifier
			.wrapContentHeight()
			.width(240.dp)
			.padding(start = 16.dp, end = 8.dp, top = 70.dp) // 👈 Offset below TopAppBar
	) {
		Column(
			modifier = Modifier.padding(16.dp),
			verticalArrangement = Arrangement.spacedBy(12.dp)
		) {
			Text("Profile", style = MaterialTheme.typography.bodyLarge)
			Text("Recipes", style = MaterialTheme.typography.bodyLarge)
			Text("New Recipe", style = MaterialTheme.typography.bodyLarge)
			Text("Drafts", style = MaterialTheme.typography.bodyLarge)
			Text("Help", style = MaterialTheme.typography.bodyLarge)
			Text("About", style = MaterialTheme.typography.bodyLarge)
		}
	}
}