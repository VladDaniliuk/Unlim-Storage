package com.shov.signinfeature.views.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import com.shov.coreui.ui.horizontalComposable
import com.shov.coreutils.values.Dialog
import com.shov.coreutils.values.Screen
import com.shov.signinfeature.views.AccountsScreen
import com.shov.signinfeature.views.AddAccountDialog
import com.shov.signinfeature.views.RevokeAccountDialog
import com.shov.signinfeature.views.SignInScreen

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.signInComposable() {
	horizontalComposable(Screen.Accounts.route) {
		AccountsScreen()
	}
	horizontalComposable(Screen.SignIn.route) {
		SignInScreen()
	}
	dialog(Dialog.AddAccount.route) {
		AddAccountDialog()
	}
	dialog(Dialog.RevokeAccount.route) {
		RevokeAccountDialog()
	}
}
