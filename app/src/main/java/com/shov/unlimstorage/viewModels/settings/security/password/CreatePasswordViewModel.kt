package com.shov.unlimstorage.viewModels.settings.security.password

import androidx.lifecycle.ViewModel
import com.shov.unlimstorage.models.repositories.PreferenceRepository
import com.shov.unlimstorage.values.PIN_CODE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CreatePasswordViewModel @Inject constructor(
	preference: PreferenceRepository,
) : ViewModel() {
	private var pass by preference.getEncryptedPref(PIN_CODE, "")

	fun onCreatePass(pass: String) {
		this.pass = pass
	}
}
