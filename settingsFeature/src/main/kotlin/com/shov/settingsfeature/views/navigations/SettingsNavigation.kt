package com.shov.settingsfeature.views.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.shov.coreutils.values.Screen
import com.shov.settingsfeature.views.*

fun NavGraphBuilder.settingsComposable(navController: NavHostController) {
	composable(Screen.ChangePassword.route) {
		ChangePasswordScreen(popBackStack = navController::popBackStack)
	}
	composable(Screen.CheckPassword.route) {
		CheckPasswordScreen {
			navController.navigate(Screen.Files.route) {
				popUpTo(Screen.CheckPassword.route) {
					inclusive = true
				}
			}
		}
	}
	composable(Screen.CreatePassword.route) {
		CreatePasswordScreen(popBackStack = navController::popBackStack)
	}
	composable(Screen.RemovePassword.route) {
		RemovePasswordScreen(popBackStack = navController::popBackStack)
	}
	composable(Screen.Security.route) {
		SecurityScreen(
			onNavigate = navController::navigate,
			popBackStack = navController::popBackStack
		)
	}
	composable(Screen.Settings.route) {
		SettingsScreen(
			navigateTo = navController::navigate,
			onBackClick = navController::popBackStack
		)
	}
}
