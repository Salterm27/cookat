package com.example.cookat.data.local.session

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "user_session")

class SessionManager(private val context: Context) {

	companion object {
		val ACCESS_TOKEN = stringPreferencesKey("access_token")
	}

	private var cachedToken: String? = null

	// Al inicializar, cargamos el token desde DataStore en cachedToken
	suspend fun loadToken() {
		cachedToken = context.dataStore.data.first()[ACCESS_TOKEN]
	}

	suspend fun saveAccessToken(token: String) {
		cachedToken = token
		context.dataStore.edit { prefs ->
			prefs[ACCESS_TOKEN] = token
		}
	}

	fun getCachedToken(): String? {
		return cachedToken
	}

	suspend fun getAccessToken(): String? {
		// En caso que no esté cargado el cachedToken, lo cargamos
		if (cachedToken == null) {
			cachedToken = context.dataStore.data.first()[ACCESS_TOKEN]
		}
		return cachedToken
	}

	suspend fun clearAccessToken() {
		cachedToken = null
		context.dataStore.edit { prefs ->
			prefs.remove(ACCESS_TOKEN)
		}
	}
}
