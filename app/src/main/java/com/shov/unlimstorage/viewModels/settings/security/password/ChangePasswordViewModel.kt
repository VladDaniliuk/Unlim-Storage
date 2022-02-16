package com.shov.unlimstorage.viewModels.settings.security.password

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.repositories.PreferenceRepository
import com.shov.unlimstorage.values.PIN_CODE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
	preference: PreferenceRepository
) : ViewModel() {
	private var pass by preference.getEncryptedPref(PIN_CODE, "")
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

