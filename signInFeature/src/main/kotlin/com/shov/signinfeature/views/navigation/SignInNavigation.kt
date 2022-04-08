package com.shov.signinfeature.views.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.shov.coreutils.values.Screen
import com.shov.signinfeature.views.AccountsScreen
import com.shov.signinfeature.views.SignInScreen

fun NavGraphBuilder.signInComposable(navController: NavHostController) {
	composable(Screen.Accounts.route) {
		AccountsScreen(onBackClick = navController::popBackStack)
	}
	composable(Screen.SignIn.route) {
		SignInScreen {
			navController.navigate(Screen.Files.route) {
				popUpTo(Screen.SignIn.route) {
					inclusive = true
				}
			}
		}
	}
}