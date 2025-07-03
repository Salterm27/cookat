package com.example.cookat.viewmodels.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookat.models.uiStates.RegisterUiState
import com.example.cookat.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class RegisterViewModel(
	private val authRepository: AuthRepository,

	) : ViewModel() {

	private val _uiState = MutableStateFlow(RegisterUiState())
	val uiState: StateFlow<RegisterUiState> = _uiState

	fun onEmailChange(value: String) {
		_uiState.value = _uiState.value.copy(email = value)
	}

	fun onPasswordChange(value: String) {
		_uiState.value = _uiState.value.copy(password = value)
	}

	fun onConfirmPasswordChange(value: String) {
		_uiState.value = _uiState.value.copy(confirmPassword = value)
	}

	fun register(onSuccess: () -> Unit) {
		viewModelScope.launch {
			_uiState.value = _uiState.value.copy(isLoading = true, error = null)

			if (_uiState.value.password != _uiState.value.confirmPassword) {
				_uiState.value = _uiState.value.copy(
					isLoading = false,
					error = "Las contraseñas no coinciden."
				)
				return@launch
			}

			val result = authRepository.signUp(
				email = _uiState.value.email,
				password = _uiState.value.password,
			)

			if (result.isSuccess) {
				onSuccess()
			} else {
				_uiState.value = _uiState.value.copy(
					isLoading = false,
					error = result.exceptionOrNull()?.message ?: "Error inesperado"
				)
			}
		}
	}


}
