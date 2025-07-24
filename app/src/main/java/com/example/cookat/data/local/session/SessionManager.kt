package com.example.cookat.data.local.session

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import io.github.jan.supabase.SupabaseClient
import kotlinx.coroutines.flow.first
import org.json.JSONObject
import android.util.Base64
import io.github.jan.supabase.auth.auth

// Declare DataStore extension
private val Context.dataStore by preferencesDataStore(name = "user_session")

class SessionManager(private val context: Context) {

	companion object {
		val ACCESS_TOKEN = stringPreferencesKey("access_token")
		val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
		val USER_ID = stringPreferencesKey("user_id")
	}

	private var cachedToken: String? = null

	// ===============================
	// Token Management
	// ===============================

	suspend fun loadToken() {
		cachedToken = context.dataStore.data.first()[ACCESS_TOKEN]
	}

	suspend fun saveAccessToken(token: String) {
		cachedToken = token
		context.dataStore.edit { prefs ->
			prefs[ACCESS_TOKEN] = token
		}
	}

	suspend fun saveTokens(accessToken: String, refreshToken: String) {
		cachedToken = accessToken
		context.dataStore.edit { prefs ->
			prefs[ACCESS_TOKEN] = accessToken
			prefs[REFRESH_TOKEN] = refreshToken
		}
	}

	fun getCachedToken(): String? {
		return cachedToken
	}

	suspend fun getAccessToken(): String? {
		if (cachedToken == null) {
			cachedToken = context.dataStore.data.first()[ACCESS_TOKEN]
		}
		return cachedToken
	}

	suspend fun clearTokens() {
		cachedToken = null
		context.dataStore.edit { prefs ->
			prefs.remove(ACCESS_TOKEN)
			prefs.remove(REFRESH_TOKEN)
			prefs.remove(USER_ID)
		}
	}

	suspend fun clearAccessToken() {
		cachedToken = null
		context.dataStore.edit { prefs ->
			prefs.remove(ACCESS_TOKEN)
		}
	}

	suspend fun isLoggedIn(): Boolean {
		val token = getAccessToken()
		return !token.isNullOrBlank()
	}

	suspend fun refreshAccessToken(supabase: SupabaseClient): Boolean {
		val refreshToken = context.dataStore.data.first()[REFRESH_TOKEN] ?: return false
		return try {
			val session = supabase.auth.refreshSession(refreshToken)
			saveTokens(session.accessToken, session.refreshToken)
			true
		} catch (e: Exception) {
			false
		}
	}

	fun isTokenExpired(token: String): Boolean {
		return try {
			val parts = token.split(".")
			if (parts.size < 2) return true
			val payload = String(Base64.decode(parts[1], Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP))
			val json = JSONObject(payload)
			val exp = json.getLong("exp")
			val now = System.currentTimeMillis() / 1000
			now >= exp
		} catch (e: Exception) {
			true
		}
	}

	suspend fun getValidAccessToken(supabase: SupabaseClient): String? {
		val token = getAccessToken()
		return if (token != null && !isTokenExpired(token)) {
			token
		} else {
			val refreshed = refreshAccessToken(supabase)
			if (refreshed) getAccessToken() else null
		}
	}

	// ===============================
	// User ID Storage
	// ===============================

	suspend fun saveUserId(userId: String) {
		context.dataStore.edit { prefs ->
			prefs[USER_ID] = userId
		}
	}

	suspend fun getUserId(): String? {
		val prefs = context.dataStore.data.first()
		return prefs[USER_ID]
	}
}