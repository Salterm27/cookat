package com.example.cookat.repository

import android.content.Context
import com.example.cookat.data.local.session.SessionManager
import com.example.cookat.data.remote.SupabaseClient
import com.example.cookat.models.dbModels.users.UserModel
import com.example.cookat.network.BackendClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.exceptions.BadRequestRestException
import retrofit2.Retrofit

class AuthRepository(
	private val sessionManager: SessionManager,
	private val context: Context
) {

	private val auth = SupabaseClient.client.auth
	private val backendApi = BackendClient.create(context)

	suspend fun requestPasswordReset(email: String): Result<Unit> {
		return try {
			val response = backendApi.requestPasswordReset(mapOf("email" to email))
			if (response.isSuccessful) {
				Result.success(Unit)
			} else {
				val errorBody = response.errorBody()?.string()
				val errorMessage = errorBody?.let {
					Regex("\"error\"\\s*:\\s*\"([^\"]+)\"").find(it)?.groupValues?.get(1)
				} ?: "Error desconocido"
				Result.failure(Exception(errorMessage))
			}
		} catch (e: Exception) {
			Result.failure(e)
		}
	}


	// Dentro de AuthRepository.kt (ya tienes los otros métodos)

	suspend fun resetPassword(email: String, code: String, newPassword: String): Result<Unit> {
		return try {
			val response = backendApi.resetPassword(
				mapOf(
					"email" to email,
					"code" to code,
					"newPassword" to newPassword
				)
			)
			if (response.isSuccessful) {
				Result.success(Unit)
			} else {
				Result.failure(Exception("Error al cambiar la contraseña: ${response.code()}"))
			}
		} catch (e: Exception) {
			Result.failure(e)
		}
	}





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
				println("Bearer token saved: $accessToken")
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

			auth.signInWith(Email) {
				this.email = email
				this.password = password
			}

			val accessToken = auth.currentSessionOrNull()?.accessToken
			if (accessToken != null) {
				sessionManager.saveAccessToken(accessToken)
				println("Bearer token after signUp: $accessToken")
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