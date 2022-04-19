package com.shov.unlimstorage.views.navigations

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.shov.autoupdatefeature.views.autoUpdateComposable
import com.shov.filesfeature.views.navigations.filesComposable
import com.shov.settingsfeature.views.navigations.settingsComposable
import com.shov.signinfeature.views.navigation.signInComposable
import com.shov.unlimstorage.viewModels.navigations.MainNavigationViewModel

@Composable
fun MainNavigation(
	mainNavigationViewModel: MainNavigationViewModel = viewModel(),
	navController: NavHostController = rememberNavController()
) {
	NavHost(
		navController = navController,
		startDestination = mainNavigationViewModel.startDestination
	) {
		autoUpdateComposable(settingsNavController = navController)
		filesComposable(filesNavController = navController)
		settingsComposable(navController = navController)
		signInComposable(navController = navController)
	}
}
