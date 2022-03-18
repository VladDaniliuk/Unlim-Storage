package com.shov.unlimstorage.viewModels.navigations

import androidx.lifecycle.ViewModel
import com.shov.preferences.datasources.PreferencesDataSource
import com.shov.preferences.values.IS_AUTH
import com.shov.preferences.values.PIN_CODE
import com.shov.unlimstorage.values.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainNavigationViewModel @Inject constructor(
	preferences: PreferencesDataSource
) : ViewModel() {
	private var isLogIn by preferences.getPref(IS_AUTH, false)
	private val pass by preferences.getEncryptedPref(PIN_CODE, "")
	val startDestination = when {
		isLogIn and pass.isEmpty() -> Screen.Files.route
		isLogIn -> Screen.CheckPassword.route
		else -> Screen.SignIn.route
	}
}
