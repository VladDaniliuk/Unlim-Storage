package com.shov.settingsfeature.viewModels.password

import androidx.lifecycle.ViewModel
import com.shov.preferences.datasources.PreferencesDataSource
import com.shov.preferences.values.PIN_CODE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CreatePasswordViewModel @Inject constructor(
	preferences: PreferencesDataSource,
) : ViewModel() {
	private var pass by preferences.getEncryptedPref(PIN_CODE, "")

	fun onCreatePass(pass: String) {
		this.pass = pass
	}
}
