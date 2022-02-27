package com.shov.unlimstorage.viewModels.settings.security

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.shov.preferences.datasources.PreferencesDataSource
import com.shov.unlimstorage.values.IS_BIOMETRIC_ENABLED
import com.shov.unlimstorage.values.PIN_CODE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SecurityViewModel @Inject constructor(preferences: PreferencesDataSource) : ViewModel() {
	private val isPassSetUpPref by preferences.getEncryptedPref(PIN_CODE, "")
	val isPassSetUp: Boolean
		get() = isPassSetUpPref.isNotEmpty()

	private var isBiometricEnabledPref by preferences.getPref(IS_BIOMETRIC_ENABLED, false)
	var isBiometricEnabled by mutableStateOf(isBiometricEnabledPref)
		private set

	fun setBiometricEnable(isEnable: Boolean) {
		isBiometricEnabledPref = isEnable
		isBiometricEnabled = isEnable
	}
}
