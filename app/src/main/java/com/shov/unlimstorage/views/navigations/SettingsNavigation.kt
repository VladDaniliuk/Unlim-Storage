package com.shov.unlimstorage.views.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.shov.unlimstorage.values.Screen
import com.shov.unlimstorage.views.settings.SettingsScreen
import com.shov.unlimstorage.views.settings.accounts.AccountsScreen
import com.shov.unlimstorage.views.settings.updates.UpdateScreen

fun NavGraphBuilder.settingsComposable(settingsNavController: NavHostController) {
	composable(Screen.Settings.route) {
		SettingsScreen(filesNavController = settingsNavController)
	}
	composable(Screen.Updates.route) {
		UpdateScreen(onBackClick = settingsNavController::popBackStack)
	}
	composable(Screen.Accounts.route) {
		AccountsScreen(onBackClick = settingsNavController::popBackStack)
	}
}
