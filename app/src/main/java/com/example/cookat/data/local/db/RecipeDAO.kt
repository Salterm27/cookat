package com.example.cookat.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cookat.data.local.entities.RecipeEntity


@Dao
interface RecipeDAO {
	@Query("SELECT * FROM recipes")
	suspend fun getRecipes(): List<RecipeEntity>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertAll(recipes: List<RecipeEntity>)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(recipe: RecipeEntity)

	@Query("DELETE FROM recipes")
	suspend fun clearRecipes()

	@Query("SELECT * FROM recipes WHERE id = :id")
	suspend fun getRecipeById(id: String): RecipeEntity?
}