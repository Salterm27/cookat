package com.example.cookat.models.dbModels.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cookat.repository.UserRepository
import com.example.cookat.viewmodels.profile.ProfileViewModel

class UserModelFactory(
	private val userRepository: UserRepository
) : ViewModelProvider.Factory {

	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
			@Suppress("UNCHECKED_CAST")
			return ProfileViewModel(userRepository) as T
		}
		throw IllegalArgumentException("Unknown ViewModel class")
	}
}
