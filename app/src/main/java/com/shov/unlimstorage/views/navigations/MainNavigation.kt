package com.shov.unlimstorage.views.navigations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.shov.autoupdatefeature.views.autoUpdateComposable
import com.shov.coreui.viewModels.NavigationViewModel
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.filesfeature.views.navigations.filesComposable
import com.shov.settingsfeature.views.navigations.settingsComposable
import com.shov.signinfeature.views.navigation.signInComposable
import com.shov.unlimstorage.viewModels.navigations.MainNavigationViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
@Composable
fun MainNavigation(
	bottomSheetNavigator: BottomSheetNavigator = rememberBottomSheetNavigator(),
	navController: NavHostController = rememberAnimatedNavController(bottomSheetNavigator),
	mainNavigationViewModel: MainNavigationViewModel = viewModel(),
	navigationViewModel: NavigationViewModel = singletonViewModel()
) {
	ModalBottomSheetLayout(
		bottomSheetNavigator = bottomSheetNavigator,
		modifier = Modifier.windowInsetsPadding(
			WindowInsets.navigationBars.only(WindowInsetsSides.Start + WindowInsetsSides.End)
		),
		sheetShape = MaterialTheme.shapes.medium.copy(
			bottomEnd = CornerSize(0.dp),
			bottomStart = CornerSize(0.dp)
		),
		sheetBackgroundColor = MaterialTheme.colorScheme.surface,
		sheetContentColor = contentColorFor(MaterialTheme.colorScheme.surface)
	) {
		AnimatedNavHost(
			navController = navController,
			startDestination = mainNavigationViewModel.startDestination
		) {
			autoUpdateComposable()
			filesComposable()
			settingsComposable()
			signInComposable()
		}
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
