package com.shov.unlimstorage.views.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.shov.coreutils.values.Screen
import com.shov.settingsfeature.views.*

fun NavGraphBuilder.securityComposable(securityNavHostController: NavHostController) {
	composable(Screen.ChangePassword.route) {
		ChangePasswordScreen(popBackStack = securityNavHostController::popBackStack)
	}
	composable(Screen.CheckPassword.route) {
		CheckPasswordScreen {
			securityNavHostController.navigate(Screen.Files.route) {
				popUpTo(Screen.CheckPassword.route) {
					inclusive = true
				}
			}
		}
	}
	composable(Screen.CreatePassword.route) {
		CreatePasswordScreen(popBackStack = securityNavHostController::popBackStack)
	}
	composable(Screen.RemovePassword.route) {
		RemovePasswordScreen(popBackStack = securityNavHostController::popBackStack)
	}
	composable(Screen.Security.route) {
		SecurityScreen(
			onNavigate = securityNavHostController::navigate,
			popBackStack = securityNavHostController::popBackStack
		)
	}
}