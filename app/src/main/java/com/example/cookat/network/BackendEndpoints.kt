package com.example.cookat.network

import com.example.cookat.models.dbModels.recipes.RecipeModel
import com.example.cookat.models.dbModels.recipes.RecipesWrapper
import com.example.cookat.network.dto.RecipeDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HEAD
import retrofit2.http.POST
import retrofit2.http.Path

interface BackendEndpoints {
	@HEAD("ping")
	suspend fun pingBackend()

	@GET("recipes")
	suspend fun getRecipes(): RecipesWrapper

	@POST("recipes")
	suspend fun createRecipe(@Body recipe: RecipeModel): RecipeModel

	@GET("recipes/{id}")
	suspend fun getRecipeById(@Path("id") id: String): RecipeDto

}