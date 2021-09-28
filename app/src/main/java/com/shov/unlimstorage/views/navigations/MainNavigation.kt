package com.shov.unlimstorage.views.navigations

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shov.unlimstorage.models.preferences.Preference
import com.shov.unlimstorage.values.IS_AUTH
import com.shov.unlimstorage.values.navMain
import com.shov.unlimstorage.values.navSignIn
import com.shov.unlimstorage.views.SignInScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun MainNavigation(
	scaffoldState: ScaffoldState,
	setTopBar: (
		prevRoute: Pair<ImageVector, (() -> Unit)>?,
		textId: Int?,
		nextRoute: Pair<ImageVector, (() -> Unit)>?
	) -> Unit
) {
	val isLogIn by Preference(LocalContext.current, IS_AUTH, false)
	val mainNavController = rememberNavController()

	NavHost(
		navController = mainNavController,
		startDestination = if (isLogIn) {
			navMain
		} else {
			navSignIn
		}
	) {
		composable(navSignIn) {
			SignInScreen(
				scaffoldState = scaffoldState,
				setTopBar = setTopBar,
				signInViewModel = hiltViewModel(),
				navController = mainNavController
			)
		}

		composable(navMain) {
			FilesNavigation(
				scaffoldState = scaffoldState,
				setTopBar = setTopBar
			)
		}
	}
}
