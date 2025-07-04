package com.example.cookat.repository

import com.example.cookat.data.local.session.SessionManager
import com.example.cookat.data.remote.SupabaseClient
import com.example.cookat.models.dbModels.users.UserModel
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.exceptions.BadRequestRestException

class AuthRepository(
	private val sessionManager: SessionManager
) {

	private val auth = SupabaseClient.client.auth

	// Login with Supabase & save token to DataStore
	suspend fun login(email: String, password: String): Result<Unit> {
		return try {
			auth.signInWith(Email) {
				this.email = email
				this.password = password
			}

			val accessToken = auth.currentSessionOrNull()?.accessToken
			if (accessToken != null) {
				sessionManager.saveAccessToken(accessToken)
				println("✅ Bearer token saved: $accessToken")
			}

			Result.success(Unit)

		} catch (e: BadRequestRestException) {
			Result.failure(Exception("Invalid email or password"))
		} catch (e: Exception) {
			Result.failure(e)
		}
	}

	// SignUp → login → store token → create user in DB
	suspend fun signUp(
		email: String,
		password: String,
		userRepository: UserRepository
	): Result<Unit> {
		return try {
			auth.signUpWith(Email) {
				this.email = email
				this.password = password
			}

			// 🔑 Login immediately to get token & user ID
			auth.signInWith(Email) {
				this.email = email
				this.password = password
			}

			val accessToken = auth.currentSessionOrNull()?.accessToken
			if (accessToken != null) {
				sessionManager.saveAccessToken(accessToken)
				println("✅ Bearer token after signUp: $accessToken")
			}

			val userId = getUserId()
			if (userId != null) {
				val user = UserModel(id = userId, email = email, username = "")
				userRepository.createUser(user)
			} else {
				throw Exception("Could not get user ID after signUp")
			}

			Result.success(Unit)

		} catch (e: BadRequestRestException) {
			Result.failure(Exception("Could not register: ${e.message}"))
		} catch (e: Exception) {
			Result.failure(e)
		}
	}

	// ✅ Log out from Supabase & clear local token
	suspend fun logout() {
		auth.signOut()
		sessionManager.clearAccessToken()
	}

	//
	fun isLoggedIn(): Boolean {
		return auth.currentSessionOrNull() != null
	}

	//
	fun getUserId(): String? {
		return auth.currentUserOrNull()?.id
	}

	fun getUserEmail(): String? {
		return auth.currentUserOrNull()?.email
	}

}