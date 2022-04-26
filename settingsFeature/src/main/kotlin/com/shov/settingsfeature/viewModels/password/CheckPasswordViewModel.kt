package com.shov.settingsfeature.viewModels.password

import androidx.lifecycle.ViewModel
import com.shov.preferences.datasources.PreferencesDataSource
import com.shov.preferences.values.IS_BIOMETRIC_ENABLED
import com.shov.preferences.values.PIN_CODE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CheckPasswordViewModel @Inject constructor(preferences: PreferencesDataSource) : ViewModel() {
	private var pass by preferences.getEncryptedPref(PIN_CODE, "")
	val isBiometricEnabled by preferences.getPref(IS_BIOMETRIC_ENABLED, false)

	fun checkPass(pass: String, onAccess: () -> Unit, onError: () -> Unit) {
		if (pass == this.pass) onAccess() else onError()
	}
}
