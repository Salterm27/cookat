package com.example.cookat.screens.recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RecipeActions(
	isFavorite: Boolean,
	onToggleFavorite: () -> Unit
) {
	Column(
		verticalArrangement = Arrangement.spacedBy(12.dp),
		horizontalAlignment = Alignment.End,
		modifier = Modifier.padding(bottom = 24.dp, end = 16.dp)
	) {
		FloatingActionButton(
			onClick = onToggleFavorite,
			shape = MaterialTheme.shapes.medium
		) {
			Icon(
				imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
				contentDescription = if (isFavorite) "Remove favorite" else "Add favorite"
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