package com.example.cookat.network

import com.example.cookat.models.dbModels.recipes.RecipeModel
import com.example.cookat.models.dbModels.recipes.RecipesWrapper
import com.example.cookat.models.dbModels.users.UserModel
import com.example.cookat.network.dto.RecipeDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HEAD
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface BackendEndpoints {

	@HEAD("ping")
	suspend fun pingBackend()

	@GET("recipes")
	suspend fun getRecipes(
		@Query("page") page: Int = 1
	): RecipesWrapper

	@GET("favourites")
	suspend fun getFavourites(): List<String>

	@POST("recipes")
	suspend fun createRecipe(@Body recipe: RecipeModel): RecipeModel

	@POST("favourites/{recipeId}")
	suspend fun addFavourite(@Path("recipeId") recipeId: String)

	@DELETE("favourites/{recipeId}")
	suspend fun removeFavourite(@Path("recipeId") recipeId: String)

	@GET("users")
	suspend fun getCurrentUser(): UserModel

	@PUT("users")
	suspend fun updateUser(@Body user: UserModel): UserModel

	@POST("users")
	suspend fun createUser(@Body user: UserModel): UserModel

	@DELETE("users")
	suspend fun deleteUser(): Unit

	@GET("recipes/{id}")
	suspend fun getRecipeById(@Path("id") id: String): RecipeDto
/*
	@POST("recipes/{id}")
	suspend fun postNewRecipe(@Path("id") id: String): RecipeDto
*/
	@POST("/auth/request-reset")
	suspend fun requestPasswordReset(@Body body: Map<String, String>): Response<Unit>

	@POST("/auth/reset-password")
	suspend fun resetPassword(@Body body: Map<String, String>): Response<Unit>

	@GET("recipes/search-own")
	suspend fun checkNewRecipeName(@Query("name") name: String): Response<Unit>
}