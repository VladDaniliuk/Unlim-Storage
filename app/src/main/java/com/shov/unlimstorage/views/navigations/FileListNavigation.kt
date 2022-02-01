package com.shov.unlimstorage.views.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shov.unlimstorage.values.Screen
import com.shov.unlimstorage.views.files.FilesScreen

@Composable
fun FileListNavigation(
	filesNavHostController: NavHostController,
	navigateToFileInfo: (String) -> Unit,
	navigateToSettings: () -> Unit
) {
	NavHost(
		navController = filesNavHostController,
		startDestination = Screen.Files.route
	) {
		composable(route = Screen.Files.route) {
			FilesScreen(
				navigateToFolder = filesNavHostController::navigate,
				navigateToFileInfo = navigateToFileInfo,
				navigateToSettings = navigateToSettings
			)
		}
	}
}
