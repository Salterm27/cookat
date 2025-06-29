package com.example.cookat.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cookat.R
import java.text.SimpleDateFormat
import java.util.*

data class RecipeCardData(
	val id: String,
	val title: String,
	val author: String,
	val vote: Float = 4.5f,
	val isFavorite: Boolean = false,
	val imageUrl: String? = null,
	val updatedAt: Date = Date()
)

@Composable
fun RecipeCard(
	recipe: RecipeCardData,
	onClick: () -> Unit,
	onToggleFavorite: (Boolean) -> Unit
) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.padding(vertical = 6.dp)
			.clickable { onClick() },
		shape = RoundedCornerShape(12.dp),
		elevation = CardDefaults.cardElevation(6.dp)
	) {
		Box {
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(12.dp),
				verticalAlignment = Alignment.CenterVertically
			) {
				val painter = painterResource(id = R.drawable.ic_recipe_placeholder)
				Image(
					painter = painter,
					contentDescription = "Recipe Image",
					modifier = Modifier
						.size(72.dp)
						.clip(RoundedCornerShape(8.dp)),
					contentScale = ContentScale.Crop
				)

				Spacer(modifier = Modifier.width(12.dp))

				Column(modifier = Modifier.weight(1f)) {
					Text(
						text = recipe.title,
						style = MaterialTheme.typography.titleMedium,
						fontWeight = FontWeight.Bold,
						maxLines = 1
					)
					Text(
						text = "By ${recipe.author}",
						style = MaterialTheme.typography.bodySmall,
						color = Color.Gray
					)

					Spacer(modifier = Modifier.height(6.dp))

					Row {
						repeat(5) { i ->
							val starFilled = i < recipe.vote.toInt()
							Icon(
								imageVector = if (starFilled) Icons.Filled.Star else Icons.Filled.StarBorder,
								contentDescription = null,
								tint = if (starFilled) Color(0xFFFFD700) else Color.Gray,
								modifier = Modifier.size(18.dp)
							)
						}
					}
				}
			}

			var favoriteState by remember { mutableStateOf(recipe.isFavorite) }

			IconButton(
				onClick = {
					favoriteState = !favoriteState
					onToggleFavorite(favoriteState)
				},
				modifier = Modifier
					.align(Alignment.TopEnd)
					.padding(8.dp)
			) {
				Icon(
					imageVector = if (favoriteState) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
					contentDescription = "Favorite",
					tint = if (favoriteState) Color.Red else Color.Gray
				)
			}

			val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
			Text(
				text = "Updated: ${formatter.format(recipe.updatedAt)}",
				style = MaterialTheme.typography.bodySmall,
				color = Color.Gray,
				modifier = Modifier
					.align(Alignment.BottomStart)
					.padding(start = 12.dp, bottom = 6.dp)
			)
		}
	}
}
