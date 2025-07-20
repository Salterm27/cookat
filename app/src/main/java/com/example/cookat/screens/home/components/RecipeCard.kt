package com.example.cookat.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cookat.R
import com.example.cookat.models.dbModels.recipes.RecipeModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RecipeCard(
	recipe: RecipeModel,
	onClick: () -> Unit,
	onToggleFavorite: (Boolean) -> Unit
) {
	val updatedAt = recipe.editedDate?.let {
		try {
			SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(it)
		} catch (e: Exception) {
			null
		}
	} ?: Date()

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
						text = "By ${recipe.username ?: recipe.userID.ifBlank { "Desconocido" }}",
						style = MaterialTheme.typography.bodySmall,
						color = Color.Gray
					)

					Spacer(modifier = Modifier.height(6.dp))

					Row(
						modifier = Modifier.fillMaxWidth(),
						verticalAlignment = Alignment.CenterVertically
					) {
						Row {
							repeat(5) { i ->
								val starFilled = i < recipe.rating.toDouble()
								Icon(
									imageVector = if (starFilled) Icons.Filled.Star else Icons.Filled.StarBorder,
									contentDescription = null,
									tint = if (starFilled) Color.Black else Color.Gray,
									modifier = Modifier.size(18.dp)
								)
							}
						}

						Spacer(modifier = Modifier.weight(1f))

						val formatter =
							remember { SimpleDateFormat("dd/MM/yy", Locale.getDefault()) }
						Text(
							text = "Updated: ${formatter.format(updatedAt)}",
							style = MaterialTheme.typography.bodySmall,
							color = Color.Gray
						)
					}
				}
			}

			IconButton(
				onClick = { onToggleFavorite(!recipe.isFavourite) },
				modifier = Modifier
					.align(Alignment.TopEnd)
					.padding(8.dp)
			) {
				Icon(
					imageVector = if (recipe.isFavourite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
					contentDescription = if (recipe.isFavourite) "Remove from favourites" else "Add to favourites",
					tint = if (recipe.isFavourite) Color.Red else Color.Gray
				)
			}
		}
	}
}
