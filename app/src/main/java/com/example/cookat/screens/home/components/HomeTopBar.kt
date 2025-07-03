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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.StarBorder
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
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeTopBar(
	drawerState: DrawerState,
	scope: CoroutineScope
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

			Surface(
				modifier = Modifier.weight(1f),
				shape = RoundedCornerShape(50),
				color = MaterialTheme.colorScheme.surfaceVariant
			) {
				Row(
					modifier = Modifier
						.padding(horizontal = 16.dp, vertical = 8.dp)
						.fillMaxWidth(),
					verticalAlignment = Alignment.CenterVertically
				) {
					Text(
						text = "Search",
						style = MaterialTheme.typography.bodyMedium,
						color = MaterialTheme.colorScheme.onSurfaceVariant,
						modifier = Modifier.weight(1f)
					)
					Icon(Icons.Filled.Search, contentDescription = "Search")
				}
			}

			Spacer(modifier = Modifier.width(8.dp))

			val iconModifier = Modifier
				.size(36.dp)
				.clip(RoundedCornerShape(12.dp))
				.background(MaterialTheme.colorScheme.surfaceVariant)

			IconButton(onClick = { /* TODO: Filter 1 */ }, modifier = iconModifier) {
				Icon(
					Icons.Filled.StarBorder,
					contentDescription = "Filter 1",
					tint = MaterialTheme.colorScheme.primary
				)
			}

			Spacer(modifier = Modifier.width(4.dp))

			IconButton(onClick = { /* TODO: Favorites */ }, modifier = iconModifier) {
				Icon(
					Icons.Filled.FavoriteBorder,
					contentDescription = "Favorites",
					tint = MaterialTheme.colorScheme.primary
				)
			}

			Spacer(modifier = Modifier.width(4.dp))

			IconButton(onClick = { /* TODO: Downloads */ }, modifier = iconModifier) {
				Icon(
					Icons.Filled.Download,
					contentDescription = "Downloads",
					tint = MaterialTheme.colorScheme.primary
				)
			}
		}
	}
}