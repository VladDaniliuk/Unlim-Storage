package com.shov.settingsfeature.views.navigations

import androidx.navigation.NavGraphBuilder
import com.shov.coreui.ui.horizontalComposable
import com.shov.coreutils.values.Screen
import com.shov.settingsfeature.views.SecurityScreen
import com.shov.settingsfeature.views.SettingsScreen
import com.shov.settingsfeature.views.password.ChangePasswordScreen
import com.shov.settingsfeature.views.password.CheckPasswordScreen
import com.shov.settingsfeature.views.password.CreatePasswordScreen
import com.shov.settingsfeature.views.password.RemovePasswordScreen
import com.shov.settingsfeature.views.theme.ThemeScreen

fun NavGraphBuilder.settingsComposable() {
	horizontalComposable(Screen.ChangePassword.route) {
		ChangePasswordScreen()
	}
	horizontalComposable(Screen.CheckPassword.route) {
		CheckPasswordScreen()
	}
	horizontalComposable(Screen.CreatePassword.route) {
		CreatePasswordScreen()
	}
	horizontalComposable(Screen.RemovePassword.route) {
		RemovePasswordScreen()
	}
	horizontalComposable(Screen.Security.route) {
		SecurityScreen()
	}
	horizontalComposable(Screen.Settings.route) {
		SettingsScreen()
	}
	horizontalComposable(Screen.Theme.route) {
		ThemeScreen()
	}
}
