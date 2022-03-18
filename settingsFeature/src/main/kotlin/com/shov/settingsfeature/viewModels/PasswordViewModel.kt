package com.shov.settingsfeature.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor() : ViewModel() {
	var pass by mutableStateOf("")
		private set
	var isPassVisible by mutableStateOf(false)
		private set

	fun changeVisibility() {
		isPassVisible = isPassVisible.not()
	}

	fun changePass(char: Int) {
		if (pass.length < 8) {
			pass += char
		}
	}

	fun submitPass(onError: () -> Unit, onRightClick: (String) -> Unit) {
		if (pass.length < 4) onError() else {
			onRightClick(pass)

			removeLastInt(true)
		}
	}

	fun removeLastInt(all: Boolean = false) {
		pass = if (all) "" else pass.substring(0 until pass.lastIndex)
	}
}
