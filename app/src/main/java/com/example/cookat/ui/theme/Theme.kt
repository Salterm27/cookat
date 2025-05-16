package com.example.cookat.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
	primary = Primary,
	onPrimary = OnPrimary,
	primaryContainer = PrimaryContainer,
	onPrimaryContainer = OnPrimaryContainer,
	secondary = Secondary,
	onSecondary = OnSecondary,
	secondaryContainer = SecondaryContainer,
	onSecondaryContainer = OnSecondaryContainer,
	background = Background,
	onBackground = OnBackground,
	surface = Surface,
	onSurface = OnSurface,
	error = Error,
	onError = OnError
)

private val DarkColorScheme = darkColorScheme(
	primary = Primary,
	onPrimary = OnPrimary,
	primaryContainer = PrimaryContainer,
	onPrimaryContainer = OnPrimaryContainer,
	secondary = Secondary,
	onSecondary = OnSecondary,
	secondaryContainer = SecondaryContainer,
	onSecondaryContainer = OnSecondaryContainer,
	background = Color(0xFF1C1B1F),
	onBackground = Color.White,
	surface = Color(0xFF1C1B1F),
	onSurface = Color.White,
	error = Error,
	onError = OnError
)

@Composable
fun CookatTheme(
	useDarkTheme: Boolean = isSystemInDarkTheme(),
	dynamicColor: Boolean = true, // Enable Material You on Android 12+
	content: @Composable () -> Unit
) {
	val colorScheme = when {
		dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
			val context = LocalContext.current
			if (useDarkTheme) dynamicDarkColorScheme(context)
			else dynamicLightColorScheme(context)
		}

		useDarkTheme -> DarkColorScheme
		else -> LightColorScheme
	}

	MaterialTheme(
		colorScheme = colorScheme,
		typography = Typography, // optional
		content = content
	)
}