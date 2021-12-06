package com.shov.unlimstorage.views.navigations

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shov.unlimstorage.models.preferences.Preference
import com.shov.unlimstorage.values.IS_AUTH
import com.shov.unlimstorage.values.Screen
import com.shov.unlimstorage.viewModels.navigations.MainNavigationViewModel
import com.shov.unlimstorage.viewModels.provider.singletonViewModel
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
}
