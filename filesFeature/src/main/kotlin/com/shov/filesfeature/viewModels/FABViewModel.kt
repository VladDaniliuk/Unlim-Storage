package com.shov.filesfeature.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.shov.coreui.ui.buttons.CustomFloatingActionButtonState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FABViewModel @Inject constructor() : ViewModel() {
	var state by mutableStateOf(CustomFloatingActionButtonState.Collapsed)
		private set

	fun collapse() {
		state = CustomFloatingActionButtonState.Collapsed
	}

	fun onClick() {
		state = if (state == CustomFloatingActionButtonState.Collapsed) {
			CustomFloatingActionButtonState.Expanded
		} else {
			CustomFloatingActionButtonState.Collapsed
		}
	}
}