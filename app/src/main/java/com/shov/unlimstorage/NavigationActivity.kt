package com.shov.unlimstorage

import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.shov.unlimstorage.ui.MainTopBar
import com.shov.unlimstorage.ui.TopBarObject
import com.shov.unlimstorage.values.*
import com.shov.unlimstorage.viewModels.MainViewModel
import com.shov.unlimstorage.views.MainFragment
import com.shov.unlimstorage.views.SignInFragment
import com.shov.unlimstorage.views.settings.SettingsFragment
import com.shov.unlimstorage.views.settings.accounts.AccountsFragment

@Composable
fun Application(mainViewModel: MainViewModel) {
	val navController = rememberNavController()
	val topBar by mainViewModel.topAppBar.collectAsState()

	Scaffold(topBar = { MainTopBar(topBarObject = topBar) }) {
		NavHost(
			navController = navController,
			startDestination = if (mainViewModel.isLogIn) navMain()
			else navSignIn
		) {
			composable(navAccounts) {
				mainViewModel.setTopAppBar(
					TopBarObject(
						navController = navController,
						prevRoute = true,
						text = R.string.accounts
					)
				)
				AccountsFragment(accountsViewModel = hiltViewModel())
			}
			composable(
				arguments = listOf(
					navArgument(argFolderId) { nullable = true },
					navArgument(argStorageType) { nullable = true }
				),
				route = navMain()
			) { backStackEntry ->
				mainViewModel.setTopAppBar(
					TopBarObject(
						navController = navController,
						nextRoute = Pair(Icons.Rounded.AccountCircle, navSettings),
						prevRoute = backStackEntry.arguments?.getString(argFolderId) != null,
						text = R.string.app_name
					)
				)
				MainFragment(navController = navController)
			}
			composable(navSettings) {
				mainViewModel.setTopAppBar(
					TopBarObject(
						navController = navController,
						prevRoute = true,
						text = R.string.settings
					)
				)
				SettingsFragment(navController = navController)
			}
			composable(navSignIn) {
				mainViewModel.setTopAppBar(
					TopBarObject(text = R.string.app_name)
				)
				SignInFragment(
					signInViewModel = hiltViewModel(),
					navController = navController
				)
			}
		}
	}
}
