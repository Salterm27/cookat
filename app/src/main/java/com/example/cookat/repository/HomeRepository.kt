package com.example.cookat.repository

import android.content.Context
import com.example.cookat.models.dbModels.RecipeModel
import com.example.cookat.network.BackendClient

class HomeRepository(context: Context) {

	private val api = BackendClient.create(context)

	suspend fun getRecipes(): Result<List<RecipeModel>> {
		return try {
			val recipes = api.getRecipes()
			Result.success(recipes)
		} catch (e: Exception) {
			Result.failure(e)
		}
	}
}