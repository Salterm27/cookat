package com.example.cookat.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cookat.data.local.entities.RecipeEntity

@Database(entities = [RecipeEntity::class], version = 5)
abstract class RecipeDB : RoomDatabase() {
	abstract fun recipeDao(): RecipeDAO

	companion object {
		@Volatile
		private var INSTANCE: RecipeDB? = null

		fun getDatabase(context: Context): RecipeDB {
			return INSTANCE ?: synchronized(this) {
				val instance = Room.databaseBuilder(
					context.applicationContext,
					RecipeDB::class.java,
					"cookat_db"
				)
					.fallbackToDestructiveMigration(true)
					.build()
				INSTANCE = instance
				instance
			}
		}
	}
}