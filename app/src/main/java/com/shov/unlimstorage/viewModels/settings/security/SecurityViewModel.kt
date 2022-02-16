package com.shov.unlimstorage.viewModels.settings.security

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.shov.unlimstorage.models.repositories.PreferenceRepository
import com.shov.unlimstorage.values.IS_BIOMETRIC_ENABLED
import com.shov.unlimstorage.values.PIN_CODE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SecurityViewModel @Inject constructor(preference: PreferenceRepository) : ViewModel() {
	private val isPassSetUpPref by preference.getEncryptedPref(PIN_CODE, "")
	val isPassSetUp: Boolean
		get() = isPassSetUpPref.isNotEmpty()

	private var isBiometricEnabledPref by preference.getPref(IS_BIOMETRIC_ENABLED, false)
	var isBiometricEnabled by mutableStateOf(isBiometricEnabledPref)
		private set

	fun setBiometricEnable(isEnable: Boolean) {
		isBiometricEnabledPref = isEnable
		isBiometricEnabled = isEnable
	}
}
