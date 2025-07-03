package com.example.cookat.repository

import com.example.cookat.data.local.session.SessionManager
import com.example.cookat.data.remote.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.exceptions.BadRequestRestException

class AuthRepository(private val sessionManager: SessionManager) {

	private val auth = SupabaseClient.client.auth

	suspend fun login(email: String, password: String): Result<Unit> {
		return try {
			auth.signInWith(Email) {
				this.email = email
				this.password = password
			}
			val accessToken = auth.currentSessionOrNull()?.accessToken
			if (accessToken != null) {
				sessionManager.saveAccessToken(accessToken)
			}
			println("Bearer token after login: $accessToken")  // <-- acá lo imprimís
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
	}

	fun isLoggedIn(): Boolean {
		return auth.currentSessionOrNull() != null
	}
}