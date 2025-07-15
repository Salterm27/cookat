package com.example.cookat.viewmodels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookat.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ForgotPasswordResetUiState(
	val email: String = "",
	val code: String = "",
	val newPassword: String = "",
	val confirmPassword: String = "",
	val isLoading: Boolean = false,
	val errorMessage: String? = null,
	val success: Boolean = false
)

class ForgotPasswordResetViewModel(
	private val authRepository: AuthRepository
) : ViewModel() {

	private val _uiState = MutableStateFlow(ForgotPasswordResetUiState())
	val uiState: StateFlow<ForgotPasswordResetUiState> = _uiState

	fun onEmailChange(value: String) {
		_uiState.value = _uiState.value.copy(email = value)
	}

	fun onCodeChange(value: String) {
		_uiState.value = _uiState.value.copy(code = value)
	}

	fun onNewPasswordChange(value: String) {
		_uiState.value = _uiState.value.copy(newPassword = value)
	}

	fun onConfirmPasswordChange(value: String) {
		_uiState.value = _uiState.value.copy(confirmPassword = value)
	}

	fun submitReset(onSuccess: () -> Unit) {
		val state = _uiState.value

		if (state.email.isBlank() || state.code.isBlank() ||
			state.newPassword.isBlank() || state.confirmPassword.isBlank()
		) {
			_uiState.value = state.copy(errorMessage = "Todos los campos son obligatorios.")
			return
		}

		if (state.newPassword != state.confirmPassword) {
			_uiState.value = state.copy(errorMessage = "Las contraseñas no coinciden.")
			return
		}

		if (state.newPassword.length < 6) {
			_uiState.value = state.copy(errorMessage = "La contraseña debe tener al menos 6 caracteres.")
			return
		}

		viewModelScope.launch {
			_uiState.value = state.copy(isLoading = true, errorMessage = null)

			val result = authRepository.resetPassword(
				email = state.email,
				code = state.code,
				newPassword = state.newPassword
			)

			_uiState.value = if (result.isSuccess) {
				state.copy(isLoading = false, success = true)
			} else {
				state.copy(
					isLoading = false,
					errorMessage = result.exceptionOrNull()?.message ?: "Error al actualizar la contraseña"
				)
			}

			if (result.isSuccess) {
				onSuccess()
			}
		}
	}
}
