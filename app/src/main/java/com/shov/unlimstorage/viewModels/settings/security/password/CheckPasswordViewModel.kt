package com.shov.unlimstorage.viewModels.settings.security.password

import androidx.lifecycle.ViewModel
import com.shov.unlimstorage.models.repositories.PreferenceRepository
import com.shov.unlimstorage.values.IS_BIOMETRIC_ENABLED
import com.shov.unlimstorage.values.PIN_CODE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CheckPasswordViewModel @Inject constructor(preference: PreferenceRepository) : ViewModel() {
	private var pass by preference.getEncryptedPref(PIN_CODE, "")
	val isBiometricEnabled by preference.getPref(IS_BIOMETRIC_ENABLED, false)

	fun checkPass(pass: String, onAccess: () -> Unit, onError: () -> Unit) {
		if (pass == this.pass) onAccess() else onError()
	}
}
