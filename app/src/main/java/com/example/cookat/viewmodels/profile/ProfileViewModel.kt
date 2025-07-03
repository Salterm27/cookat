package com.example.cookat.viewmodels.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookat.models.uiStates.ProfileUiState
import com.example.cookat.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
	private val userRepository: UserRepository
) : ViewModel() {

	private val _uiState = MutableStateFlow(ProfileUiState())
	val uiState: StateFlow<ProfileUiState> = _uiState

	init {
		loadProfile()
	}

	fun loadProfile() {
		viewModelScope.launch {
			try {
				_uiState.update { it.copy(isLoading = true) }

				val user = userRepository.getCurrentUser()
				val emailValue = user?.email ?: ""
				val usernameValue = user?.username ?: ""

				_uiState.update {
					it.copy(
						originalEmail = emailValue,
						originalUsername = usernameValue,
						editedEmail = emailValue,
						editedUsername = usernameValue,
						isLoading = false,
						error = null
					)
				}
			} catch (e: Exception) {
				_uiState.update {
					it.copy(
						error = e.message ?: "Unknown error",
						isLoading = false
					)
				}
			}
		}
	}

	fun onEmailChange(newEmail: String) {
		_uiState.update { it.copy(editedEmail = newEmail) }
	}

	fun onUsernameChange(newUsername: String) {
		_uiState.update { it.copy(editedUsername = newUsername) }
	}

	fun showDialog() {
		_uiState.update { it.copy(showConfirmationDialog = true) }
	}

	fun dismissDialog() {
		_uiState.update { it.copy(showConfirmationDialog = false) }
	}

	fun confirmUpdate(onSuccess: () -> Unit) {
		viewModelScope.launch {
			updateProfile()
			_uiState.update { it.copy(showConfirmationDialog = false) }
			onSuccess()
		}
	}

	private suspend fun updateProfile() {
		_uiState.update { it.copy(isLoading = true) }
		try {
			val currentUser = userRepository.getCurrentUser()
			if (currentUser != null) {
				val updatedUser = currentUser.copy(
					email = _uiState.value.editedEmail,
					username = _uiState.value.editedUsername
				)

				val result = userRepository.updateUser(updatedUser)

				val newEmail = result?.email ?: _uiState.value.editedEmail
				val newUsername = result?.username ?: _uiState.value.editedUsername

				_uiState.update {
					it.copy(
						originalEmail = newEmail,
						originalUsername = newUsername,
						editedEmail = newEmail,
						editedUsername = newUsername,
						isLoading = false,
						error = null
					)
				}
			} else {
				throw Exception("No current user loaded")
			}
		} catch (e: Exception) {
			_uiState.update {
				it.copy(
					error = e.message ?: "Unknown error",
					isLoading = false
				)
			}
		}
	}
}