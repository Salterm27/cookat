package com.example.cookat.screens.settings

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
fun MySettings(onNavigateTo: () -> Unit) {
	Scaffold { padding ->
		Column(
			modifier = Modifier
				.padding(padding)
				.fillMaxSize(),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Text(text = "Pagina de Configuracion")
			Button(onClick = onNavigateTo) {
				Text("Volver")
			}
		}
	}
}
