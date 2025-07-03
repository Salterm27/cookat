package com.example.cookat.repository

import android.content.Context
import com.example.cookat.data.local.db.RecipeDB
import com.example.cookat.data.local.mapper.toEntity
import com.example.cookat.data.local.mapper.toModel
import com.example.cookat.models.dbModels.recipes.RecipeModel
import com.example.cookat.network.BackendClient

class HomeRepository(context: Context) {

	private val api = BackendClient.create(context)
	private val dao = RecipeDB.getDatabase(context).recipeDao()

	suspend fun getLocalRecipes(): List<RecipeModel> {
		return dao.getRecipes().map { it.toModel() }
	}

	suspend fun refreshRecipesFromBackend() {
		val response = api.getRecipes() // returns RecipesWrapper
		val remoteRecipes = response.results

		// Optional: sync favourites if needed

		dao.clearRecipes()
		dao.insertAll(remoteRecipes.map { it.toEntity() })
	}
}