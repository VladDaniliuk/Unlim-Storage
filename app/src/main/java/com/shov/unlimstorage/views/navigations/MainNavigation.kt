package com.shov.unlimstorage.views.navigations

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shov.unlimstorage.values.Screen
import com.shov.unlimstorage.viewModels.navigations.MainNavigationViewModel
import com.shov.unlimstorage.views.signIn.SignInScreen

@Composable
fun MainNavigation(
	mainNavigationViewModel: MainNavigationViewModel = viewModel(),
	navHostController: NavHostController = rememberNavController()
) {
	NavHost(
		navController = navHostController,
		startDestination = mainNavigationViewModel.startDestination
	) {
		composable(Screen.SignIn.route) {
			SignInScreen {
				navHostController.navigate(Screen.Files.route) {
					popUpTo(Screen.SignIn.route) {
						inclusive = true
					}
				}
			}
		}
		filesComposable(filesNavController = navHostController)
		securityComposable(securityNavHostController = navHostController)
		settingsComposable(settingsNavController = navHostController)
	}
}
