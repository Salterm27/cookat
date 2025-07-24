package com.example.cookat.repository

import android.content.Context
import com.example.cookat.data.local.db.RecipeDB
import com.example.cookat.data.local.mapper.toEntity
import com.example.cookat.data.local.mapper.toModel
import com.example.cookat.models.dbModels.recipes.RecipeModel
import com.example.cookat.network.BackendClient
import com.example.cookat.network.dto.RecipeSentDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepository(context: Context) {

	private val api = BackendClient.create(context)
	private val dao = RecipeDB.getDatabase(context).recipeDao()

	suspend fun getRecipeById(id: String, isFavourite: Boolean = false): Result<RecipeModel> {
		return withContext(Dispatchers.IO) {
			try {
				val remote = api.getRecipeById(id)
				dao.insert(remote.toEntity(isFavourite))
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

	suspend fun submitRecipe(dto: RecipeSentDto): Result<Unit> {
		return withContext(Dispatchers.IO) {
			try {
				val response = api.createRecipe(dto)
				if (response.isSuccessful) {
					Result.success(Unit)
				} else {
					Result.failure(Exception("API error: ${response.code()} ${response.message()}"))
				}
			} catch (e: Exception) {
				Result.failure(e)
			}
		}
	}
}