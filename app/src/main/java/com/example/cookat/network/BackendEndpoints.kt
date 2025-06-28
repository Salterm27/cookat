package com.example.cookat.network

import com.example.cookat.models.dbModels.RecipeModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BackendEndpoints {
	@GET("recipes")
	suspend fun getRecipes(): List<RecipeModel>

	@POST("recipes")
	suspend fun createRecipe(@Body recipe: RecipeModel): RecipeModel
}