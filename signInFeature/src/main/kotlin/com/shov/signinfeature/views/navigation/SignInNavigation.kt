package com.shov.signinfeature.views.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import com.shov.coreutils.values.Dialog
import com.shov.coreutils.values.Screen
import com.shov.signinfeature.views.AccountsScreen
import com.shov.signinfeature.views.AddAccountDialog
import com.shov.signinfeature.views.RevokeAccountDialog
import com.shov.signinfeature.views.SignInScreen

fun NavGraphBuilder.signInComposable() {
	composable(Screen.Accounts.route) {
		AccountsScreen()
	}
	composable(Screen.SignIn.route) {
		SignInScreen()
	}
	dialog(Dialog.AddAccount.route) {
		AddAccountDialog()
	}
	dialog(Dialog.RevokeAccount.route) {
		RevokeAccountDialog()
	}
}
