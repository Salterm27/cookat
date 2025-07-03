package com.example.cookat.viewmodels.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookat.models.uiStates.LoginUiState
import com.example.cookat.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(
	private val authRepository: AuthRepository
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

			val result = authRepository.login(uiState.email.trim(), uiState.password)

			if (result.isSuccess) {
				uiState = uiState.copy(isLoading = false)
				onSuccess()
			} else {
				val rawError = result.exceptionOrNull()?.message ?: ""
				val friendlyMessage = when {
					rawError.contains("invalid_credentials", ignoreCase = true) -> {
						"El email o la contraseña son incorrectos."
					}

					else -> {
						"Ocurrió un error inesperado. Intenta de nuevo."
					}
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