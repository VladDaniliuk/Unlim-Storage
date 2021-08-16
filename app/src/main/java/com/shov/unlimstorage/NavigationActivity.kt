package com.shov.unlimstorage

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shov.unlimstorage.values.navMain
import com.shov.unlimstorage.values.navSettings
import com.shov.unlimstorage.values.navSignIn
import com.shov.unlimstorage.viewModels.SignInViewModel
import com.shov.unlimstorage.views.MainFragment
import com.shov.unlimstorage.views.SettingsFragment
import com.shov.unlimstorage.views.SignInFragment

@Composable
fun Application(signInViewModel: SignInViewModel) {
	val navController = rememberNavController()

	NavHost(
		navController = navController,
		startDestination = if (signInViewModel.isLogIn) navMain
		else navSignIn
	) {
		composable(navMain) { MainFragment(navController = navController) }
		composable(navSettings) { SettingsFragment() }
		composable(navSignIn) {
			SignInFragment(
				signInViewModel = hiltViewModel(),
				navController = navController
			)
		}
	}
}
