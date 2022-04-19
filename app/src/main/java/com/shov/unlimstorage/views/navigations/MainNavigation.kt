package com.shov.unlimstorage.views.navigations

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.shov.autoupdatefeature.views.autoUpdateComposable
import com.shov.filesfeature.views.navigations.filesComposable
import com.shov.settingsfeature.views.navigations.settingsComposable
import com.shov.signinfeature.views.navigation.signInComposable
import com.shov.unlimstorage.viewModels.navigations.MainNavigationViewModel

@Composable
fun MainNavigation(
	navController: NavHostController,
	mainNavigationViewModel: MainNavigationViewModel = viewModel(),
) {
	NavHost(
		navController = navController,
		startDestination = mainNavigationViewModel.startDestination
	) {
		autoUpdateComposable(settingsNavController = navController)
		filesComposable(navController)
		settingsComposable(navController = navController)
		signInComposable(navController = navController)
	}
}
