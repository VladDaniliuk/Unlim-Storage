package com.shov.coreui.viewModels

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScaffoldViewModel @Inject constructor() : ViewModel() {
	//Snackbar
	val snackbarHostState = SnackbarHostState()

	fun showSnackbar(message: String) {
		viewModelScope.launch {
			snackbarHostState.showSnackbar(message)
		}
	}
}
