package com.example.cookat.viewmodels.home

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookat.models.uiStates.HomeUiState
import com.example.cookat.repository.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel(context: Context) : ViewModel() {

	private val repository = HomeRepository(context)

	var uiState by mutableStateOf(HomeUiState())
		private set

	init {
		loadRecipes()
	}

	fun loadRecipes() {
		viewModelScope.launch {
			uiState = uiState.copy(isLoading = true)
			val result = repository.getRecipes()
			uiState = if (result.isSuccess) {
				uiState.copy(isLoading = false, recipes = result.getOrNull() ?: emptyList())
			} else {
				uiState.copy(isLoading = false, errorMessage = result.exceptionOrNull()?.message)
			}
		}
	}
}