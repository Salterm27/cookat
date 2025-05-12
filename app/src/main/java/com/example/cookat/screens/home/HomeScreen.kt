package com.example.cookat.screens.home

import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.example.cookat.screens.home.sidemenu.DrawerContent
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.ui.Modifier
import androidx.compose.material3.TopAppBar
import kotlinx.coroutines.launch
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
	val drawerState = rememberDrawerState(DrawerValue.Closed)
	val scope = rememberCoroutineScope()
	ModalNavigationDrawer(
		drawerState = drawerState,
		drawerContent = { DrawerContent(navController) }
	) {
		Scaffold(
			topBar = {
				TopAppBar(
					title = { Text("Home") },
					navigationIcon = {
						IconButton(onClick = { scope.launch { drawerState.open() } }) {
							Icon(Icons.Default.Menu, contentDescription = "Menu")
						}
					},
					actions = {
						IconButton(onClick = { /* TODO: Search */ }) {
							Icon(Icons.Default.Search, contentDescription = "Search")
						}
						IconButton(onClick = { /* TODO: Save */ }) {
							Icon(Icons.Default.FavoriteBorder, contentDescription = "Favorites")
						}
					}
				)
			}
		) { padding ->
			Column(
				modifier = Modifier
					.padding(padding)
					.padding(16.dp)
					.fillMaxSize()
			) {

				Spacer(modifier = Modifier.height(16.dp))
			}
		}
	}
}