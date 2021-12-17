package com.shov.unlimstorage.viewModels.navigations

import androidx.compose.material.ExperimentalMaterialApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shov.unlimstorage.models.MainNavigationViewModelFactory
import com.shov.unlimstorage.models.preferences.Preference
import com.shov.unlimstorage.values.Screen
import com.shov.unlimstorage.values.UNCHECKED_CAST
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class MainNavigationViewModel @AssistedInject constructor(
	@Assisted private var _isLogInPref: Preference<Boolean>
) : ViewModel() {
	private var isLogIn by _isLogInPref
	var startDestination = if (isLogIn) Screen.Files.route else Screen.SignIn.route
		private set

	fun setIsLogIn() {
		isLogIn = true
	}

	@Suppress(UNCHECKED_CAST)
	companion object {
		@OptIn(ExperimentalMaterialApi::class)
		fun provideFactory(
			assistedFactory: MainNavigationViewModelFactory,
			isLogInPref: Preference<Boolean>
		): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
			override fun <T : ViewModel?> create(modelClass: Class<T>): T {
				return assistedFactory.createMainNavigationViewModel(isLogInPref) as T
			}
		}
	}
}
