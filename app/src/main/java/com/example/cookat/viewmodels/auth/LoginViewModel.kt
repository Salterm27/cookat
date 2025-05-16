package com.example.cookat.viewmodels.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookat.models.auth.LoginUiState
import kotlinx.coroutines.launch


class LoginViewModel : ViewModel() {
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
			uiState = uiState.copy(isLoading = true)
			if (uiState.email == "test@example.com" && uiState.password == "password") {
				uiState = uiState.copy(isLoading = false, errorMessage = null)
				onSuccess()
			} else {
				uiState = uiState.copy(
					isLoading = false,
					password = "",
					errorMessage = "Invalid email or password"
				)
			}
		}
	}
}