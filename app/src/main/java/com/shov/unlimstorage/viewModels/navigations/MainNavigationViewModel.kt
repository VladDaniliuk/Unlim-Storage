package com.shov.unlimstorage.viewModels.navigations

import androidx.lifecycle.ViewModel
import com.shov.unlimstorage.models.repositories.PreferenceRepository
import com.shov.unlimstorage.values.IS_AUTH
import com.shov.unlimstorage.values.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainNavigationViewModel @Inject constructor(preference: PreferenceRepository) : ViewModel() {
	private var isLogIn by preference.getPref(IS_AUTH, false)
	val startDestination = if (isLogIn) Screen.Files.route else Screen.SignIn.route

	fun setIsLogIn() {
		isLogIn = true
	}
}
