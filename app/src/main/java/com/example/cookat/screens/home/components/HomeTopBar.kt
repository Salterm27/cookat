package com.example.cookat.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.cookat.models.uiStates.RecipeFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeTopBar(
	drawerState: DrawerState,
	scope: CoroutineScope,
	currentFilter: RecipeFilter,
	searchQuery: String,
	onFilterChange: (RecipeFilter) -> Unit,
	onSearchChange: (String) -> Unit
) {
	Surface(
		modifier = Modifier
			.fillMaxWidth()
			.statusBarsPadding(),
		tonalElevation = 3.dp,
		color = MaterialTheme.colorScheme.surface
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 12.dp, vertical = 8.dp),
			verticalAlignment = Alignment.CenterVertically
		) {
			IconButton(onClick = { scope.launch { drawerState.open() } }) {
				Icon(Icons.Filled.Menu, contentDescription = "Open Menu")
			}

			Spacer(modifier = Modifier.width(8.dp))

			// ✅ Rounded search bar with BasicTextField
			Surface(
				modifier = Modifier.weight(1f),
				shape = RoundedCornerShape(50),
				color = MaterialTheme.colorScheme.surfaceVariant
			) {
				BasicTextField(
					value = searchQuery,
					onValueChange = { onSearchChange(it) },
					singleLine = true,
					textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
					modifier = Modifier
						.fillMaxWidth()
						.padding(horizontal = 16.dp, vertical = 12.dp),
					decorationBox = { innerTextField ->
						if (searchQuery.isEmpty()) {
							Text(
								text = "Search recipes...",
								style = MaterialTheme.typography.bodyMedium,
								color = MaterialTheme.colorScheme.onSurfaceVariant
							)
						}
						innerTextField()
					}
				)
			}

			Spacer(modifier = Modifier.width(8.dp))

			val iconModifier = Modifier
				.size(36.dp)
				.clip(RoundedCornerShape(12.dp))
				.background(MaterialTheme.colorScheme.surfaceVariant)

			IconButton(
				onClick = {
					if (currentFilter == RecipeFilter.FAVOURITES) {
						onFilterChange(RecipeFilter.ALL)
					} else {
						onFilterChange(RecipeFilter.FAVOURITES)
					}
				},
				modifier = iconModifier
			) {
				Icon(
					imageVector = if (currentFilter == RecipeFilter.FAVOURITES) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
					contentDescription = "Filter favourites",
					tint = if (currentFilter == RecipeFilter.FAVOURITES) Color.Red else MaterialTheme.colorScheme.primary
				)
			}
		}
	}
}