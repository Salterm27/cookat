package com.example.cookat.screens.home.components.sidemenu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun DrawerItem(title: String, onClick: (() -> Unit)? = null) {
	val isEnabled = onClick != null
	val textColor = if (isEnabled) {
		MaterialTheme.colorScheme.onSurface
	} else {
		MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f) // faded look
	}

	Text(
		text = title,
		style = MaterialTheme.typography.bodyLarge,
		color = textColor,
		modifier = Modifier
			.fillMaxWidth()
			.then(
				if (isEnabled) {
					Modifier
						.clickable(onClick = onClick!!)
						.padding(vertical = 8.dp)
				} else {
					Modifier.padding(vertical = 8.dp)
				}
			)
	)
}