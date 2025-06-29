package com.example.cookat.repository

import android.content.Context
import com.example.cookat.data.local.session.SessionManager
import com.example.cookat.data.remote.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.exceptions.BadRequestRestException

class AuthRepository(private val context: Context) {

	private val auth = SupabaseClient.client.auth
	private val sessionManager = SessionManager(context)

	suspend fun login(email: String, password: String): Result<Unit> {
		return try {
			val sessionResult = auth.signInWith(Email) {
				this.email = email
				this.password = password
			}
			val token = auth.currentSessionOrNull()?.accessToken
				?: throw Exception("Login succeeded but session is null — no access token!")
			sessionManager.saveAccessToken(token)
			Result.success(Unit)
		} catch (e: BadRequestRestException) {
			Result.failure(Exception("Invalid email or password"))
		} catch (e: Exception) {
			Result.failure(e)
		}
	}

	suspend fun signUp(email: String, password: String): Result<Unit> {
		return try {
			auth.signUpWith(Email) {
				this.email = email
				this.password = password
			}
			Result.success(Unit)
		} catch (e: BadRequestRestException) {
			Result.failure(Exception("Could not register: ${e.message}"))
		} catch (e: Exception) {
			Result.failure(e)
		}
	}

	suspend fun logout() {
		auth.signOut()
		sessionManager.clearAccessToken()
	}

	fun isLoggedIn(): Boolean {
		return auth.currentSessionOrNull() != null
	}
}