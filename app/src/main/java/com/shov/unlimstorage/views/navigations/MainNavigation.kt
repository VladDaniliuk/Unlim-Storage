package com.shov.unlimstorage.views.navigations

import androidx.appcompat.app.AppCompatActivity
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
import com.shov.unlimstorage.views.SignInScreen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainNavigation(
	mainNavigationViewModel: MainNavigationViewModel,
	sheetContent: MutableState<(@Composable ColumnScope.() -> Unit)?>
) {
	val isLogIn by Preference(LocalContext.current, IS_AUTH, false)
	val mainNavController = rememberNavController()
	val context = LocalContext.current as AppCompatActivity

	NavHost(
		navController = mainNavController,
		startDestination = if (isLogIn) {
			Screen.Files.route
		} else {
			Screen.SignIn.route
		}
	) {
		composable(Screen.SignIn.route) {
			SignInScreen(
				navController = mainNavController,
				scaffoldState = mainNavigationViewModel.scaffoldState,
				signInViewModel = hiltViewModel(),
				topAppBarViewModel = hiltViewModel(context)
			)
		}
		filesComposable(
			filesNavController = mainNavController,
			scaffoldState = mainNavigationViewModel.scaffoldState,
			sheetContent = sheetContent,
			sheetState = mainNavigationViewModel.sheetState
		)
		settingsComposable(settingsNavController = mainNavController)
	}
}
