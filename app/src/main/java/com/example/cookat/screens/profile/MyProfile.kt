package com.example.cookat.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun MyProfile(onNavigateTo: () -> Unit) {
	Scaffold { padding ->
		Column(
			modifier = Modifier
				.padding(padding)
				.fillMaxSize(),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Text(text = "Profile page")
			Button(onClick = onNavigateTo) {
				Text("Submit")
			}
		}
	}
}