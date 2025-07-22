package com.example.cookat.screens.recipes.editor.WizardScreens.Component.Ingredients

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cookat.models.uiStates.Ingredient
import com.example.cookat.ui.functions.verticalFadeEdge

@Composable
fun IngredientList(
	ingredients: List<Ingredient>,
	onRemoveIngredient: (Int) -> Unit,
	modifier: Modifier = Modifier
) {
	val listState = rememberLazyListState()

	// Auto scroll to last
	LaunchedEffect(ingredients.size) {
		if (ingredients.isNotEmpty()) {
			listState.animateScrollToItem(ingredients.size - 1)
		}
	}

	// Determine if we should show the fades
	val showTopFade =
		listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 0
	val showBottomFade = remember(ingredients.size, listState) {
		val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
		ingredients.size > 0 && lastVisible < ingredients.size - 1
	}

	Box(
		modifier = modifier
			.verticalFadeEdge(
				topFade = showTopFade,
				bottomFade = showBottomFade,
				fadeHeight = 36f // or tweak for your theme
			)
	) {
		LazyColumn(
			state = listState,
			modifier = Modifier.fillMaxWidth(),
			contentPadding = PaddingValues(bottom = 60.dp)
		) {
			itemsIndexed(ingredients) { idx, ingredient ->
				IngredientDisplayRow(
					ingredient = ingredient,
					onRemove = { onRemoveIngredient(idx) }
				)
			}
		}
	}
}