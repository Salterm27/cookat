package com.example.cookat.viewmodels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookat.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ForgotPasswordUiState(
	val email: String = "",
	val isLoading: Boolean = false,
	val errorMessage: String? = null,
	val success: Boolean = false
)

class ForgotPasswordViewModel(
	private val authRepository: AuthRepository
) : ViewModel() {

	private val _uiState = MutableStateFlow(ForgotPasswordUiState())
	val uiState: StateFlow<ForgotPasswordUiState> = _uiState

	fun onEmailChange(value: String) {
		_uiState.value = _uiState.value.copy(email = value)
	}

	fun submitRequest() {
		viewModelScope.launch {
			_uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
			val result = authRepository.requestPasswordReset(_uiState.value.email)

			_uiState.value = if (result.isSuccess) {
				_uiState.value.copy(isLoading = false, success = true)
			} else {
				_uiState.value.copy(isLoading = false, errorMessage = result.exceptionOrNull()?.message)
			}
		}
	}
}
