package com.shov.autoupdatefeature.views

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.shov.coreutils.values.Screen


fun NavGraphBuilder.autoUpdateComposable(settingsNavController: NavHostController) {
	composable(Screen.Updates.route) {
		UpdateScreen(onBackClick = settingsNavController::popBackStack)
	}
}