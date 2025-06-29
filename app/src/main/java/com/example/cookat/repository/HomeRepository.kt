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

	suspend fun getRecipes(): Result<List<RecipeModel>> {
		return withContext(Dispatchers.IO) {
			try {
				// 1️⃣ Try local DB first
				val localRecipes = dao.getRecipes().map { it.toModel() }
				if (localRecipes.isNotEmpty()) {
					return@withContext Result.success(localRecipes)
				}

				// 2️⃣ If empty, fetch from backend
				val response = api.getRecipes() // RecipesResponse
				val remoteRecipes = response.results

				// 3️⃣ Save to local DB
				dao.clearRecipes()
				dao.insertAll(remoteRecipes.map { it.toEntity() })

				Result.success(remoteRecipes)

			} catch (e: Exception) {
				Result.failure(e)
			}
		}
	}
}