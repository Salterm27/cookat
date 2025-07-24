package com.example.cookat.data.local.session

import android.content.Context
import com.example.cookat.data.local.db.RecipeDB
import com.example.cookat.data.local.session.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDataCleaner(
	private val context: Context,
	private val sessionManager: SessionManager,
	private val recipeDB: RecipeDB
) {

	suspend fun clearLocalUserData() = withContext(Dispatchers.IO) {
		// Clear token / session
		sessionManager.clearAccessToken()

		// Clear all recipe-related local data
		recipeDB.recipeDao().clearRecipes()

		// Optional: clear cache directory (e.g., downloaded images, temp files)
		context.cacheDir.deleteRecursively()
	}
}