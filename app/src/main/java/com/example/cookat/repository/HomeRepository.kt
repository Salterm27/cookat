package com.example.cookat.repository

import android.content.Context
import com.example.cookat.models.dbModels.recipes.RecipeModel
import com.example.cookat.network.BackendClient

class HomeRepository(context: Context) {

	private val api = BackendClient.create(context)

	suspend fun getRecipes(): Result<List<RecipeModel>> {
		return try {
			val response = api.getRecipes() // gets RecipesResponse
			Result.success(response.results) // unwrap the list you actually need
		} catch (e: Exception) {
			Result.failure(e)
		}
	}
}