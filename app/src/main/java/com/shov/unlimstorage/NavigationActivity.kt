package com.shov.unlimstorage

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shov.unlimstorage.ui.MainTopBar
import com.shov.unlimstorage.ui.TopBarObject
import com.shov.unlimstorage.values.navAccounts
import com.shov.unlimstorage.values.navMain
import com.shov.unlimstorage.values.navSettings
import com.shov.unlimstorage.values.navSignIn
import com.shov.unlimstorage.viewModels.MainViewModel
import com.shov.unlimstorage.views.MainFragment
import com.shov.unlimstorage.views.SignInFragment
import com.shov.unlimstorage.views.settings.AccountsFragment
import com.shov.unlimstorage.views.settings.SettingsFragment

@ExperimentalMaterialApi
@Composable
fun Application(mainViewModel: MainViewModel) {
	val navController = rememberNavController()
	val topBar by mainViewModel.topAppBar.collectAsState()

	Scaffold(topBar = { MainTopBar(topBarObject = topBar) }) {
		NavHost(
			navController = navController,
			startDestination = if (mainViewModel.isLogIn) navMain
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
				AccountsFragment()
			}
			composable(navMain) {
				mainViewModel.setTopAppBar(
					TopBarObject(
						navController = navController,
						nextRoute = Pair(Icons.Rounded.AccountCircle, navSettings),
						text = R.string.app_name
					)
				)
				MainFragment()
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
