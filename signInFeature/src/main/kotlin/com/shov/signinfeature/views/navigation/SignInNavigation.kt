package com.shov.signinfeature.views.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import com.shov.coreutils.values.Dialog
import com.shov.coreutils.values.Screen
import com.shov.signinfeature.views.AccountsScreen
import com.shov.signinfeature.views.AddAccountDialog
import com.shov.signinfeature.views.RevokeAccountDialog
import com.shov.signinfeature.views.SignInScreen

fun NavGraphBuilder.signInComposable(navController: NavHostController) {
	composable(Screen.Accounts.route) {
		AccountsScreen(
			onBackClick = navController::popBackStack,
			navigateToRevokeAccountDialog = { storageType ->
				navController.navigate(Dialog.RevokeAccount.setStorageType(storageType.name))
			}
		) {
			navController.navigate(Dialog.AddAccount.route)
		}
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
	dialog(Dialog.AddAccount.route) {
		AddAccountDialog {
			navController.navigate(Screen.Accounts.route) {
				popUpTo(Screen.Accounts.route) {
					inclusive = true
				}
			}
		}
	}
	dialog(Dialog.RevokeAccount.route) {
		RevokeAccountDialog {
			navController.navigate(Screen.Accounts.route) {
				popUpTo(Screen.Accounts.route) {
					inclusive = true
				}
			}
		}
	}
}
