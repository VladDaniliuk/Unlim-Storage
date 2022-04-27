package com.shov.unlimstorage.views.navigations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.shov.autoupdatefeature.views.autoUpdateComposable
import com.shov.coreui.viewModels.NavigationViewModel
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.filesfeature.views.navigations.filesComposable
import com.shov.settingsfeature.views.navigations.settingsComposable
import com.shov.signinfeature.views.navigation.signInComposable
import com.shov.unlimstorage.viewModels.navigations.MainNavigationViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun MainNavigation(
	navController: NavHostController,
	mainNavigationViewModel: MainNavigationViewModel = viewModel(),
	navigationViewModel: NavigationViewModel = singletonViewModel()
) {
	NavHost(
		navController = navController,
		startDestination = mainNavigationViewModel.startDestination
	) {
		autoUpdateComposable()
		filesComposable()
		settingsComposable()
		signInComposable(navController = navController)
	}

	LaunchedEffect(key1 = null) {
		navigationViewModel.route.onEach { route ->
			if (route.isEmpty()) navController.popBackStack() else {
				navController.navigate(route) {
					navigationViewModel.popUpRoute.let { popUpRoute ->
						if (popUpRoute.isNotEmpty()) {
							popUpTo(popUpRoute) {
								inclusive = navigationViewModel.inclusive
							}
						}
					}
				}
			}
		}.launchIn(this)
	}
}
