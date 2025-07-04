package com.example.cookat.repository

import android.content.Context
import com.example.cookat.data.local.db.RecipeDB
import com.example.cookat.data.local.mapper.toEntity
import com.example.cookat.data.local.mapper.toModel
import com.example.cookat.models.dbModels.recipes.RecipeModel
import com.example.cookat.network.BackendClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeRepository(context: Context) {

	private val api = BackendClient.create(context)
	private val dao = RecipeDB.getDatabase(context).recipeDao()

	suspend fun getLocalRecipes(): List<RecipeModel> {
		return withContext(Dispatchers.IO) {
			dao.getRecipes().map { it.toModel() }
		}
	}

	suspend fun refreshRecipesFromBackend() {
		withContext(Dispatchers.IO) {
			val response = api.getRecipes()
			val remoteRecipes = response.results

			dao.clearRecipes()
			dao.insertAll(remoteRecipes.map { it.toEntity() })

			syncFavourites()
		}
	}

	suspend fun syncFavourites() {
		withContext(Dispatchers.IO) {
			runCatching {
				val localRecipes = dao.getRecipes()
				val remoteFavouriteIds: Set<String> = api.getFavourites().toSet()

				localRecipes.forEach { recipe ->
					val shouldBeFavourite = remoteFavouriteIds.contains(recipe.id)
					if (recipe.isFavourite != shouldBeFavourite) {
						dao.updateFavourite(recipe.id, shouldBeFavourite)
					}
				}
			}.onFailure {
				// Optional: log or handle error
			}
		}
	}

	suspend fun toggleFavourite(recipeId: String, newState: Boolean) {
		withContext(Dispatchers.IO) {
			// Update local DB immediately for instant feedback
			dao.updateFavourite(recipeId, newState)

			// Sync with backend
			if (newState) {
				api.addFavourite(recipeId)
			} else {
				api.removeFavourite(recipeId)
			}
		}
	}
}