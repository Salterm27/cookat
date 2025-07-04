package com.example.cookat.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cookat.data.local.entities.RecipeEntity

@Dao
interface RecipeDAO {

	// with live updates: HomeScreen stays in sync automatically.
	@Query("SELECT * FROM recipes")
	fun observeRecipes(): kotlinx.coroutines.flow.Flow<List<RecipeEntity>>

	// for one-time blocking calls, e.g., for syncing favourites.
	@Query("SELECT * FROM recipes")
	suspend fun getRecipes(): List<RecipeEntity>

	// for  RecipeDetails screen to load a single recipe.
	@Query("SELECT * FROM recipes WHERE id = :id")
	suspend fun getRecipeById(id: String): RecipeEntity?

	// Standard insert for single recipe — works for details or other single adds.
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(recipe: RecipeEntity)

	// for each page of results in pagination!
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertAll(recipes: List<RecipeEntity>)

	// Local toggle: updates the `isFavourite` flag immediately.
	@Query("UPDATE recipes SET isFavourite = :state WHERE id = :id")
	suspend fun updateFavourite(id: String, state: Boolean)

	// full refresh (e.g., pull-to-refresh).
	@Query("DELETE FROM recipes")
	suspend fun clearRecipes()
}