package com.example.cookat.models.uiStates

data class
LoginUiState(
	val email: String = "",
	val password: String = "",
	val isLoading: Boolean = false,
	val errorMessage: String? = null
)
