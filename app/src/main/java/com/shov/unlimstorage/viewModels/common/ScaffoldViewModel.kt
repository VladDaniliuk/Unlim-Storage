package com.shov.unlimstorage.viewModels.common

import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScaffoldViewModel @Inject constructor() : ViewModel() {
	val scaffoldState = ScaffoldState(DrawerState(DrawerValue.Closed), SnackbarHostState())

	fun showSnackbar(message: String) {
		viewModelScope.launch {
			scaffoldState.snackbarHostState.showSnackbar(message)
		}
	}
}
