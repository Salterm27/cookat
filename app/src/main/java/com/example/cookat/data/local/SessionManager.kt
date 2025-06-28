package com.example.cookat.data.local

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

	suspend fun saveAccessToken(token: String) {
		context.dataStore.edit { prefs ->
			prefs[ACCESS_TOKEN] = token
		}
	}

	suspend fun getAccessToken(): String? {
		val prefs = context.dataStore.data.first()
		return prefs[ACCESS_TOKEN]
	}

	suspend fun clearAccessToken() {
		context.dataStore.edit { prefs ->
			prefs.remove(ACCESS_TOKEN)
		}
	}
}