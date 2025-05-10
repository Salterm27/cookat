package com.example.cookat.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun RegisterScreen(onNavigateTo: () -> Unit) {
	Scaffold { padding ->
		Column(
			modifier = Modifier
				.padding(padding)
				.fillMaxSize(),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
		) {
			Text(text = "Register Screen")
			Button(onClick = onNavigateTo) {
				Text("Submit")
			}
		}
	}
}
