package com.shov.unlimstorage.viewModels.settings.security.password

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.shov.preferences.datasources.PreferencesDataSource
import com.shov.preferences.values.PIN_CODE
import com.shov.unlimstorage.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
	preferences: PreferencesDataSource
) : ViewModel() {
	private var pass by preferences.getEncryptedPref(PIN_CODE, "")
	var isPassChecked by mutableStateOf(false)
		private set
	val titleId: Int
		get() = if (isPassChecked) R.string.set_new_password else R.string.check_password

	private fun checkPass(pass: String) = (this.pass == pass).also { isPassChecked ->
		this.isPassChecked = isPassChecked
	}

	fun onRightClick(pass: String, onSuccess: () -> Unit, onError: () -> Unit) {
		if (isPassChecked) {
			this.pass = pass
			onSuccess()
		} else if (checkPass(pass).not()) {
			onError()
		}
	}
}
