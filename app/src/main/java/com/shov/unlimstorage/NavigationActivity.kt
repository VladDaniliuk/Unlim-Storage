package com.shov.unlimstorage

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shov.unlimstorage.viewModels.SignInViewModel
import com.shov.unlimstorage.views.MainFragment
import com.shov.unlimstorage.views.SignInFragment

@Composable
fun Application(signInViewModel: SignInViewModel) {
	val navController = rememberNavController()

	NavHost(
		navController = navController,
		startDestination = if (signInViewModel.isLogIn) stringResource(R.string.nav_main)
		else stringResource(R.string.nav_sign_in)
	) {
		composable("nav_main") { MainFragment() }
		composable("nav_signIn") { SignInFragment(hiltViewModel(), navController) }
	}
}
