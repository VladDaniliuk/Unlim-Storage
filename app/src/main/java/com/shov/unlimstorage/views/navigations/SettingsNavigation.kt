package com.shov.unlimstorage.views.navigations

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.shov.unlimstorage.values.Screen
import com.shov.unlimstorage.viewModels.provider.singletonViewModel
import com.shov.unlimstorage.viewModels.provider.updateViewModel
import com.shov.unlimstorage.views.settings.SettingsScreen
import com.shov.unlimstorage.views.settings.accounts.AccountsScreen
import com.shov.unlimstorage.views.settings.updates.UpdateScreen

fun NavGraphBuilder.settingsComposable(settingsNavController: NavController) {
	composable(Screen.Settings.route) {
		SettingsScreen(
			filesNavController = settingsNavController,
			topAppBarViewModel = singletonViewModel()
		)
	}
	composable(Screen.Updates.route) {
		UpdateScreen(filesNavController = settingsNavController)
	}
	composable(Screen.Accounts.route) {
		AccountsScreen(
			accountsViewModel = hiltViewModel(),
			filesNavController = settingsNavController,
			topAppBarViewModel = singletonViewModel()
		)
	}
}
