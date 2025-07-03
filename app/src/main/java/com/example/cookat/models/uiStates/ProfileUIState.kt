package com.example.cookat.models.uiStates

data class ProfileUiState(
	val originalEmail: String = "",
	val originalUsername: String = "",
	val editedEmail: String = "",
	val editedUsername: String = "",
	val isLoading: Boolean = false,
	val error: String? = null,
	val showConfirmationDialog: Boolean = false
) {
	val isChanged: Boolean
		get() = editedEmail != originalEmail || editedUsername != originalUsername
}