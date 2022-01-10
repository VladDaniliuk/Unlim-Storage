package com.shov.unlimstorage.views.navigations

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shov.unlimstorage.models.repositories.signIn.CheckDropboxCredential
import com.shov.unlimstorage.values.Screen
import com.shov.unlimstorage.viewModels.navigations.MainNavigationViewModel
import com.shov.unlimstorage.views.SignInScreen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainNavigation(
	mainNavigationViewModel: MainNavigationViewModel,
	navController: NavHostController = rememberNavController(),
	sheetContent: MutableState<(@Composable ColumnScope.() -> Unit)?>
) {
	NavHost(
		navController = navController,
		startDestination = mainNavigationViewModel.startDestination
	) {
		composable(Screen.SignIn.route) {
			SignInScreen(
				navController = navController,
				scaffoldState = mainNavigationViewModel.scaffoldState,
			)
		}
		filesComposable(
			filesNavController = navController,
			scaffoldState = mainNavigationViewModel.scaffoldState,
			sheetContent = sheetContent,
			sheetState = mainNavigationViewModel.sheetState
		)
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
