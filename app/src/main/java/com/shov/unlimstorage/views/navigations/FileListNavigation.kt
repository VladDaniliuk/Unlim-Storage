package com.shov.unlimstorage.views.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shov.coreutils.values.Screen
import com.shov.filesfeature.views.files.FilesScreen

@Composable
fun FileListNavigation(
	navHostController: NavHostController,
	filesNavHostController: NavHostController,
	onBackPressed: () -> Unit
) {
	NavHost(
		navController = filesNavHostController,
		startDestination = Screen.Files.route
	) {
		composable(route = Screen.Files.route) {
			FilesScreen(
				navHostController = navHostController,
				onBackPress = onBackPressed
			) { backStack ->
				filesNavHostController.navigate(Screen.Files.openFolder(backStack))
			}
		}
	}
}
