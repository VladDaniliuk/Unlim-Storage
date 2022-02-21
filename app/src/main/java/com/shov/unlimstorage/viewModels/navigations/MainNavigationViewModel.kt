package com.shov.unlimstorage.viewModels.navigations

import androidx.lifecycle.ViewModel
import com.shov.unlimstorage.models.repositories.PreferenceRepository
import com.shov.unlimstorage.values.IS_AUTH
import com.shov.unlimstorage.values.PIN_CODE
import com.shov.unlimstorage.values.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainNavigationViewModel @Inject constructor(preference: PreferenceRepository) : ViewModel() {
	private var isLogIn by preference.getPref(IS_AUTH, false)
	private val pass by preference.getEncryptedPref(PIN_CODE, "")
	val startDestination = when {
		isLogIn and pass.isEmpty() -> Screen.Files.route
		isLogIn -> Screen.CheckPassword.route
		else -> Screen.SignIn.route
	}
}
