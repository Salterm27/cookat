package com.example.cookat.repository

import android.content.Context
import com.example.cookat.data.local.session.SessionManager
import com.example.cookat.models.dbModels.users.UserModel
import com.example.cookat.network.BackendClient
import com.example.cookat.network.BackendEndpoints
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(
	private val backendApi: BackendEndpoints,
	private val sessionManager: SessionManager
) {

	// Método para obtener token guardado y mostrarlo
	private suspend fun getToken(): String? {
		val token = sessionManager.getAccessToken()
		println("Using Bearer token in UserRepository: $token")
		return token
	}



	suspend fun getCurrentUser(): UserModel? {
		val token = getToken() ?: return null
		// Aquí tu cliente Retrofit debería inyectar automáticamente el header Authorization con el token
		return backendApi.getCurrentUser()
	}

	suspend fun createUser(user: UserModel) {

		backendApi.createUser(user)
	}

	suspend fun updateUser(user: UserModel): UserModel? {
		val token = getToken() ?: return null
		return backendApi.updateUser(user)
	}

	suspend fun deleteUser() {
		val token = getToken() ?: return
		backendApi.deleteUser()
	}
}