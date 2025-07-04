package com.example.cookat.screens.home.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.cookat.models.uiStates.HomeUiState

@Composable
fun HomeContent(
	modifier: Modifier = Modifier,
	state: HomeUiState,
	navController: NavController,
	onToggleFavorite: (String, Boolean) -> Unit,
	onLoadMore: (Int, Int) -> Unit
) {
	val listState = rememberLazyListState()

	LazyColumn(
		state = listState,
		modifier = modifier
	) {
		items(state.recipes) { recipe ->
			RecipeCard(
				recipe = recipe,
				onClick = {
					navController.navigate("recipe/${recipe.id}")
				},
				onToggleFavorite = { isFav ->
					onToggleFavorite(recipe.id, isFav)
				}
			)
		}
	}

	LaunchedEffect(listState) {
		snapshotFlow {
			val layoutInfo = listState.layoutInfo
			layoutInfo.visibleItemsInfo.lastOrNull()?.index to layoutInfo.totalItemsCount
		}.collect { (lastVisible, totalItems) ->
			val safeLastVisible = lastVisible ?: 0
			onLoadMore(safeLastVisible, totalItems)
		}
	}
}