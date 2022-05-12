package com.shov.filesfeature.views

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.shov.coreui.ui.horizontalComposable
import com.shov.coreutils.values.Screen
import com.shov.filesfeature.views.files.FilesScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FileListNavigation(
	filesNavHostController: NavHostController,
	onBackPressed: () -> Unit
) {
	AnimatedNavHost(
		navController = filesNavHostController,
		startDestination = Screen.Files.route
	) {
		horizontalComposable(Screen.Files.route) {
			FilesScreen(onBackPress = onBackPressed) { backStack ->
				filesNavHostController.navigate(Screen.Files.openFolder(backStack))
			}
		}
	}
}
