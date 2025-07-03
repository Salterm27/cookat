package com.example.cookat.models.uiStates

data class RegisterUiState(
	val email: String = "",
	val password: String = "",
	val confirmPassword: String = "",
	val isLoading: Boolean = false,
	val error: String? = null
)