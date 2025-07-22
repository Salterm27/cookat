package com.example.cookat.ui.functions

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun Modifier.verticalFadeEdge(
	topFade: Boolean = true,
	bottomFade: Boolean = true,
	fadeHeight: Float = 40f,
	fadeColor: Color = Color.White
) = this.drawWithContent {
	drawContent()
	if (topFade) {
		drawRect(
			brush = Brush.verticalGradient(
				colors = listOf(fadeColor, Color.Transparent),
				startY = 0f,
				endY = fadeHeight
			)
		)
	}
	if (bottomFade) {
		drawRect(
			brush = Brush.verticalGradient(
				colors = listOf(Color.Transparent, fadeColor),
				startY = size.height - fadeHeight,
				endY = size.height
			)
		)
	}
}