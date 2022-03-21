package com.shov.unlimstorage.views.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.shov.coreutils.values.Screen
import com.shov.unlimstorage.views.settings.SettingsScreen
import com.shov.signinfeature.views.AccountsScreen
import com.shov.unlimstorage.views.settings.updates.UpdateScreen

fun NavGraphBuilder.settingsComposable(settingsNavController: NavHostController) {
	composable(Screen.Accounts.route) {
		AccountsScreen(onBackClick = settingsNavController::popBackStack)
	}
	composable(Screen.Settings.route) {
		SettingsScreen(
			navigateTo = settingsNavController::navigate,
			onBackClick = settingsNavController::popBackStack
		)
	}
	composable(Screen.Updates.route) {
		UpdateScreen(onBackClick = settingsNavController::popBackStack)
	}
}
