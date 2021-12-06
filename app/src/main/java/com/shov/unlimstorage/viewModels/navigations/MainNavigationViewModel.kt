package com.shov.unlimstorage.viewModels.navigations

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shov.unlimstorage.models.MainNavigationViewModelFactory
import com.shov.unlimstorage.models.preferences.Preference
import com.shov.unlimstorage.values.Screen
import com.shov.unlimstorage.values.UNCHECKED_CAST
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class MainNavigationViewModel @OptIn(ExperimentalMaterialApi::class) @AssistedInject constructor(
	@Assisted private val _scaffoldState: ScaffoldState,
	@Assisted private val _sheetState: ModalBottomSheetState,
	@Assisted private var _isLogInPref: Preference<Boolean>
) : ViewModel() {
	private var _isLogIn by _isLogInPref
	var isLogIn by mutableStateOf(_isLogIn)
		private set
	var startDestination = if (_isLogIn) Screen.Files.route else Screen.SignIn.route

	val scaffoldState get() = _scaffoldState

	@OptIn(ExperimentalMaterialApi::class)
	val sheetState = _sheetState

	fun setIsLogIn() {
		_isLogIn = true
	}

	@Suppress(UNCHECKED_CAST)
	companion object {
		@OptIn(ExperimentalMaterialApi::class)
		fun provideFactory(
			assistedFactory: MainNavigationViewModelFactory,
			scaffoldState: ScaffoldState,
			sheetState: ModalBottomSheetState,
			isLogInPref: Preference<Boolean>
		): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
			override fun <T : ViewModel?> create(modelClass: Class<T>): T {
				return assistedFactory.createMainNavigationViewModel(
					scaffoldState,
					sheetState,
					isLogInPref
				) as T
			}
		}
	}
}
