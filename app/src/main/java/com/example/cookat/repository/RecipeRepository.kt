package com.example.cookat.repository

import android.content.Context
import com.example.cookat.data.local.db.RecipeDB
import com.example.cookat.data.local.mapper.toEntity
import com.example.cookat.data.local.mapper.toModel
import com.example.cookat.models.dbModels.recipes.RecipeModel
import com.example.cookat.network.BackendClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepository(context: Context) {

	private val api = BackendClient.create(context)
	private val dao = RecipeDB.getDatabase(context).recipeDao()

	suspend fun getRecipeById(id: String): Result<RecipeModel> {
		return withContext(Dispatchers.IO) {
			try {
				val local = dao.getRecipeById(id)?.toModel()
				if (local != null) return@withContext Result.success(local)

				val remote = api.getRecipeById(id)
				dao.insert(remote.toEntity())
				Result.success(remote.toModel())

			} catch (e: Exception) {
				Result.failure(e)
			}
		}
	}

	//
	suspend fun updateFavouriteLocal(recipeId: String, newState: Boolean) {
		withContext(Dispatchers.IO) {
			dao.updateFavourite(recipeId, newState)
		}
	}

	//
	suspend fun apiAddFavourite(recipeId: String) {
		withContext(Dispatchers.IO) {
			api.addFavourite(recipeId)
		}
	}

	//
	suspend fun apiRemoveFavourite(recipeId: String) {
		withContext(Dispatchers.IO) {
			api.removeFavourite(recipeId)
		}
	}
}