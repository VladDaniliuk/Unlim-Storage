package com.shov.unlimstorage.viewModels.settings.security.password

import androidx.lifecycle.ViewModel
import com.shov.preferences.datasources.PreferencesDataSource
import com.shov.unlimstorage.values.PIN_CODE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RemovePasswordViewModel @Inject constructor(
	preferences: PreferencesDataSource
) : ViewModel() {
	private var pass by preferences.getEncryptedPref(PIN_CODE, "")

	fun checkPass(pass: String, onAccess: () -> Unit, onError: () -> Unit) {
		if (pass == this.pass) onAccess() else onError()
	}

	fun removePass() {
		pass = ""
	}
}
