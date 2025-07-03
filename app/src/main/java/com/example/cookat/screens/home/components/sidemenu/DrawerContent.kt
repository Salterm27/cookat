package com.example.cookat.screens.home.components.sidemenu


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun DrawerContent(navController: NavController) {
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
			DrawerItem("Profile") { navController.navigate("profile") }
			DrawerItem("Recipes")
			DrawerItem("New Recipe") { navController.navigate("newRecipe") }
			DrawerItem("Drafts")
			DrawerItem("Settings") { navController.navigate("settings") }
		}
	}
}