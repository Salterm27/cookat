package com.example.cookat.viewmodels.auth

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookat.models.uiStates.LoginUiState
import com.example.cookat.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(
	private val context: Context,
	private val authRepository: AuthRepository = AuthRepository(context)
) : ViewModel() {

	var uiState by mutableStateOf(LoginUiState())
		private set

	fun onEmailChange(newEmail: String) {
		uiState = uiState.copy(email = newEmail)
	}

	fun onPasswordChange(newPassword: String) {
		uiState = uiState.copy(password = newPassword)
	}

	fun login(onSuccess: () -> Unit) {
		viewModelScope.launch {
			uiState = uiState.copy(isLoading = true, errorMessage = null)

			val result = authRepository.login(uiState.email, uiState.password)

			if (result.isSuccess) {
				uiState = uiState.copy(isLoading = false)
				onSuccess()
			} else {
				val rawMessage = result.exceptionOrNull()?.message.orEmpty()

				
				val friendlyMessage = when {
					rawMessage.contains("invalid_credentials", ignoreCase = true) ->
						"Email o contraseña incorrectos"

					rawMessage.contains("network", ignoreCase = true) ->
						"Error de conexión. Verifica tu internet."

					else -> "Ups, algo salió mal. Intenta de nuevo."
				}

				uiState = uiState.copy(
					isLoading = false,
					password = "",
					errorMessage = friendlyMessage
				)
			}
		}
	}
}