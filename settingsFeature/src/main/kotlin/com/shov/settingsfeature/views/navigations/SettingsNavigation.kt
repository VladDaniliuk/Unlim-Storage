package com.shov.settingsfeature.views.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.shov.coreutils.values.Screen
import com.shov.settingsfeature.views.SecurityScreen
import com.shov.settingsfeature.views.SettingsScreen
import com.shov.settingsfeature.views.password.ChangePasswordScreen
import com.shov.settingsfeature.views.password.CheckPasswordScreen
import com.shov.settingsfeature.views.password.CreatePasswordScreen
import com.shov.settingsfeature.views.password.RemovePasswordScreen
import com.shov.settingsfeature.views.theme.ThemeScreen

fun NavGraphBuilder.settingsComposable() {
	composable(Screen.ChangePassword.route) {
		ChangePasswordScreen()
	}
	composable(Screen.CheckPassword.route) {
		CheckPasswordScreen()
	}
	composable(Screen.CreatePassword.route) {
		CreatePasswordScreen()
	}
	composable(Screen.RemovePassword.route) {
		RemovePasswordScreen()
	}
	composable(Screen.Security.route) {
		SecurityScreen()
	}
	composable(Screen.Settings.route) {
		SettingsScreen()
	}
	composable(Screen.Theme.route) {
		ThemeScreen()
	}
}
