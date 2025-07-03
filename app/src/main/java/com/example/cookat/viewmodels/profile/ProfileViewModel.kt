package com.example.cookat.viewmodels.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookat.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


data class UserProfileState(
	val email: String = "",
	val username: String = "",
	val isLoading: Boolean = false,
	val error: String? = null
)

class ProfileViewModel(
	private val userRepository: UserRepository
) : ViewModel() {

	private val _state = MutableStateFlow(UserProfileState())
	val state: StateFlow<UserProfileState> = _state

	init {
		loadProfile()
	}

	fun loadProfile() {
		viewModelScope.launch {
			try {
				_state.value = _state.value.copy(isLoading = true)

				val user = userRepository.getCurrentUser()

				_state.value = _state.value.copy(
					email = user?.email ?: "",
					username = user?.username ?: "", // depende de tu modelo UserModel
					isLoading = false
				)

			} catch (e: Exception) {
				_state.value = _state.value.copy(
					error = e.message ?: "Unknown error",
					isLoading = false
				)
			}
		}
	}


	fun updateProfile(newEmail: String, newUsername: String) {
		viewModelScope.launch {
			try {
				_state.value = _state.value.copy(isLoading = true)

				val currentUser = userRepository.getCurrentUser()
				if (currentUser != null) {
					val updatedUser = currentUser.copy(
						email = newEmail,
						username = newUsername
					)

					val result = userRepository.updateUser(updatedUser)

					_state.value = _state.value.copy(
						email = result?.email ?: newEmail,
						username = result?.username ?: newUsername,
						isLoading = false
					)
				} else {
					throw Exception("No current user loaded")
				}
			} catch (e: Exception) {
				_state.value = _state.value.copy(
					error = e.message ?: "Unknown error",
					isLoading = false
				)
			}
		}
	}
}