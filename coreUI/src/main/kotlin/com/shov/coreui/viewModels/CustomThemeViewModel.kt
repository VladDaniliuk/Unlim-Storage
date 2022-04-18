package com.shov.coreui.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.shov.preferences.datasources.PreferencesDataSource
import com.shov.preferences.values.IS_DYNAMIC_THEME
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CustomThemeViewModel @Inject constructor(preferences: PreferencesDataSource) : ViewModel() {
	private var isDynamicThemePref by preferences.getPref(IS_DYNAMIC_THEME, false)
	var isDynamicTheme by mutableStateOf(isDynamicThemePref)
		private set

	fun changeDynamicTheme(isDynamicTheme: Boolean) {
		isDynamicThemePref = isDynamicTheme
		this.isDynamicTheme = isDynamicTheme
	}
}