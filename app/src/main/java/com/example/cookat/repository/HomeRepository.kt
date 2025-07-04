package com.example.cookat.repository

import android.content.Context
import com.example.cookat.data.local.db.RecipeDB
import com.example.cookat.data.local.mapper.toEntity
import com.example.cookat.data.local.mapper.toModel
import com.example.cookat.models.dbModels.recipes.RecipeModel
import com.example.cookat.network.BackendClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HomeRepository(context: Context) {

	private val api = BackendClient.create(context)
	private val dao = RecipeDB.getDatabase(context).recipeDao()

	fun observeRecipes(): Flow<List<RecipeModel>> {
		return dao.observeRecipes().map { entities ->
			entities.map { it.toModel() }
		}
	}

	suspend fun refreshRecipesFromBackend() {
		val response = api.getRecipes(page = 1)
		val remoteRecipes = response.results
		dao.clearRecipes()
		dao.insertAll(remoteRecipes.map { it.toEntity() })
		syncFavourites()
	}

	suspend fun fetchRecipesPage(page: Int) {
		val response = api.getRecipes(page)
		val remoteRecipes = response.results
		dao.insertAll(remoteRecipes.map { it.toEntity() })
	}

	suspend fun syncFavourites() {
		val localRecipes = dao.getRecipes()
		val remoteFavouriteIds = api.getFavourites().toSet()

		localRecipes.forEach { recipe ->
			val shouldBeFavourite = remoteFavouriteIds.contains(recipe.id)
			if (recipe.isFavourite != shouldBeFavourite) {
				dao.updateFavourite(recipe.id, shouldBeFavourite)
			}
		}
	}

	suspend fun toggleFavourite(recipeId: String, newState: Boolean) {
		dao.updateFavourite(recipeId, newState)
		if (newState) api.addFavourite(recipeId) else api.removeFavourite(recipeId)
	}
}