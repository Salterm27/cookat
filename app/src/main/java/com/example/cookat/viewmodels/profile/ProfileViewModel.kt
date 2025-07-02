package com.example.cookat.viewmodels.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookat.data.remote.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import org.slf4j.MDC.put

data class UserProfileState(
	val email: String = "",
	val username: String = "",
	val isLoading: Boolean = false,
	val error: String? = null
)

class ProfileViewModel : ViewModel() {

	private val _state = MutableStateFlow(UserProfileState())
	val state: StateFlow<UserProfileState> = _state

	init {
		loadProfile()
	}

	fun loadProfile() {
		viewModelScope.launch {
			try {
				_state.value = _state.value.copy(isLoading = true)

				val user = SupabaseClient.client.auth.currentSessionOrNull()?.user

				_state.value = _state.value.copy(
					email = user?.email ?: "",
					username = user?.userMetadata?.get("username")?.toString() ?: "",
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

				val currentUser = SupabaseClient.client.auth.currentSessionOrNull()?.user

				SupabaseClient.client.auth.updateUser {
					if (currentUser?.email != newEmail) {
						this.email = newEmail
					}
					data = buildJsonObject {
						put("username", newUsername)
					}
				}

				SupabaseClient.client.auth.refreshCurrentSession()

				val updatedUser = SupabaseClient.client.auth.currentSessionOrNull()?.user

				_state.value = _state.value.copy(
					email = updatedUser?.email ?: newEmail,
					username = updatedUser?.userMetadata?.get("username")?.toString()
						?: newUsername,
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
}