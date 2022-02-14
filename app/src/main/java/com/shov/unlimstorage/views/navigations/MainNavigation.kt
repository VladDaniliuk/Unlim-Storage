package com.shov.unlimstorage.views.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shov.unlimstorage.models.repositories.signIn.CheckDropboxCredential
import com.shov.unlimstorage.values.Screen
import com.shov.unlimstorage.viewModels.navigations.MainNavigationViewModel
import com.shov.unlimstorage.views.signIn.SignInScreen

@Composable
fun MainNavigation(
	mainNavigationViewModel: MainNavigationViewModel,
	navController: NavHostController = rememberNavController()
) {
	NavHost(
		navController = navController,
		startDestination = mainNavigationViewModel.startDestination
	) {
		composable(Screen.SignIn.route) {
			SignInScreen {
				navController.navigate(Screen.Files.route) {
					popUpTo(Screen.SignIn.route) {
						inclusive = true
					}
				}
			}
		}
		filesComposable(filesNavController = navController)
		settingsComposable(settingsNavController = navController)
	}

	CheckDropboxCredential {
		mainNavigationViewModel.setIsLogIn()

		if (navController.currentBackStackEntry?.destination?.route == Screen.SignIn.route) {
			navController.navigate(Screen.Files.route) {
				popUpTo(Screen.SignIn.route) {
					inclusive = true
				}
			}
		}
	}
}
