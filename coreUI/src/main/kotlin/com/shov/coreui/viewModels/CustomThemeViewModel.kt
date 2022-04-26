package com.shov.coreui.viewModels

import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.shov.coreui.models.Theme
import com.shov.preferences.datasources.PreferencesDataSource
import com.shov.preferences.values.IS_DARK_THEME
import com.shov.preferences.values.IS_DYNAMIC_THEME
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CustomThemeViewModel @Inject constructor(preferences: PreferencesDataSource) : ViewModel() {
	private var isDynamicThemePref by preferences.getPref(IS_DYNAMIC_THEME, false)
	var isDynamicTheme by mutableStateOf(
		isDynamicThemePref.and(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
	)
		private set

	private var themePref by preferences.getPref(IS_DARK_THEME, Theme.System.name)
	var theme by mutableStateOf(Theme.valueOf(themePref))
		private set

	fun changeDynamicTheme(isDynamicTheme: Boolean) {
		isDynamicThemePref = isDynamicTheme
		this.isDynamicTheme = isDynamicTheme
	}

	fun changeTheme(theme: Theme) {
		themePref = theme.name
		this.theme = theme
	}
}
