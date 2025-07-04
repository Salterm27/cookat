package com.example.cookat.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
	primary = CookatPrimary,
	onPrimary = CookatOnPrimary,
	secondary = CookatSecondary,
	background = CookatBackground,
	surface = CookatSurface,
	error = CookatError,
	outline = CookatOutline
)

private val DarkColors = darkColorScheme(
	primary = CookatPrimary,
	onPrimary = CookatOnPrimary,
	secondary = CookatSecondary,
	background = Color(0xFF121212),
	surface = Color(0xFF1E1E1E),
	error = CookatError,
	outline = CookatOutline
)

@Composable
fun CookatTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	content: @Composable () -> Unit
) {
	val colors = if (darkTheme) DarkColors else LightColors

	MaterialTheme(
		colorScheme = colors,
		typography = CookatTypography,
		content = content
	)
}