package com.example.cookat.viewmodels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookat.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ResetPasswordUiState(
	val email: String = "",
	val code: String = "",
	val newPassword: String = "",
	val confirmPassword: String = "",
	val isLoading: Boolean = false,
	val errorMessage: String? = null,
	val success: Boolean = false
)

class ResetPasswordViewModel(
	private val authRepository: AuthRepository
) : ViewModel() {

	private val _uiState = MutableStateFlow(ResetPasswordUiState())
	val uiState: StateFlow<ResetPasswordUiState> = _uiState

	fun setEmail(email: String) {
		_uiState.value = _uiState.value.copy(email = email)
	}

	fun onCodeChange(value: String) {
		if (value.length <= 6 && value.all { it.isDigit() }) {
			_uiState.value = _uiState.value.copy(code = value)
		}
	}


	fun onNewPasswordChange(value: String) {
		_uiState.value = _uiState.value.copy(newPassword = value)
	}

	fun onConfirmPasswordChange(value: String) {
		_uiState.value = _uiState.value.copy(confirmPassword = value)
	}
	fun submitReset(email: String, onResult: (Boolean) -> Unit) {
		viewModelScope.launch {
			_uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

			val result = authRepository.resetPassword(
				email = email,
				code = _uiState.value.code,
				newPassword = _uiState.value.newPassword
			)

			if (result.isSuccess) {
				_uiState.value = _uiState.value.copy(isLoading = false, success = true)
				onResult(true)
			} else {
				_uiState.value = _uiState.value.copy(
					isLoading = false,
					errorMessage = result.exceptionOrNull()?.message ?: "Error inesperado"
				)
				onResult(false)
			}
		}
	}

	fun setErrorMessage(message: String) {
		_uiState.update { it.copy(errorMessage = message) }
	}


	fun submit(onSuccess: () -> Unit) {
		val state = _uiState.value

		// Validaciones básicas
		if (state.code.isBlank() || state.newPassword.isBlank() || state.confirmPassword.isBlank()) {
			_uiState.value = state.copy(errorMessage = "Todos los campos son obligatorios")
			return
		}

		if (state.code.length != 6 || !state.code.all { it.isDigit() }) {
			_uiState.value = state.copy(errorMessage = "El código debe tener 6 dígitos numéricos")
			return
		}

		if (state.newPassword.length < 8) {
			_uiState.value = state.copy(errorMessage = "La contraseña debe tener al menos 8 caracteres")
			return
		}

		if (state.newPassword != state.confirmPassword) {
			_uiState.value = state.copy(errorMessage = "Las contraseñas no coinciden")
			return
		}

		viewModelScope.launch {
			_uiState.value = state.copy(isLoading = true, errorMessage = null)

			val result = authRepository.resetPassword(
				email = state.email,
				code = state.code,
				newPassword = state.newPassword
			)

			if (result.isSuccess) {
				_uiState.value = _uiState.value.copy(isLoading = false, success = true)
				onSuccess()
			} else {
				_uiState.value = _uiState.value.copy(
					isLoading = false,
					errorMessage = result.exceptionOrNull()?.message ?: "Error al cambiar contraseña"
				)
			}
		}
	}
}
