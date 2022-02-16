package com.shov.unlimstorage.viewModels.settings.security.password

import androidx.lifecycle.ViewModel
import com.shov.unlimstorage.models.repositories.PreferenceRepository
import com.shov.unlimstorage.values.PIN_CODE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RemovePasswordViewModel @Inject constructor(preference: PreferenceRepository) : ViewModel() {
	private var pass by preference.getEncryptedPref(PIN_CODE, "")

	fun checkPass(pass: String, onAccess: () -> Unit, onError: () -> Unit) {
		if (pass == this.pass) onAccess() else onError()
	}

	fun removePass() {
		pass = ""
	}
}
