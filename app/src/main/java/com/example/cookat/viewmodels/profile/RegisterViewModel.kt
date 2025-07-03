package com.example.cookat.viewmodels.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookat.repository.AuthRepository
import com.example.cookat.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class RegisterUiState(
	val email: String = "",
	val password: String = "",
	val confirmPassword: String = "",
	val isLoading: Boolean = false,
	val error: String? = null
)

class RegisterViewModel(
	private val authRepository: AuthRepository,
	private val userRepository: UserRepository
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
		val state = _uiState.value

		if (state.password != state.confirmPassword) {
			_uiState.value = state.copy(error = "Las contraseñas no coinciden")
			return
		}

		viewModelScope.launch {
			_uiState.value = state.copy(isLoading = true, error = null)

			val result = authRepository.signUp(state.email.trim(), state.password)
			if (result.isSuccess) {
				// crear usuario en tu base
				val id = authRepository.getUserId()
				if (id != null) {
					userRepository.createUser(id, state.email)
					_uiState.value = _uiState.value.copy(isLoading = false)
					onSuccess()
				} else {
					_uiState.value = state.copy(isLoading = false, error = "No se pudo obtener el ID")
				}
			} else {
				_uiState.value = state.copy(
					isLoading = false,
					error = result.exceptionOrNull()?.message ?: "Error desconocido"
				)
			}
		}
	}
}
