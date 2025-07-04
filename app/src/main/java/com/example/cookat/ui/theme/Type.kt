package com.example.cookat.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val CookatTypography = Typography(
	displayLarge = TextStyle(
		fontWeight = FontWeight.Bold,
		fontSize = 36.sp
	),
	titleMedium = TextStyle(
		fontWeight = FontWeight.Medium,
		fontSize = 20.sp
	),
	bodyLarge = TextStyle(
		fontSize = 16.sp
	),
	labelMedium = TextStyle(
		fontSize = 14.sp,
		fontWeight = FontWeight.Medium
	),
	labelLarge = TextStyle(
		fontSize = 16.sp,
		fontWeight = FontWeight.Bold
	)

)