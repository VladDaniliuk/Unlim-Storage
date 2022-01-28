package com.shov.unlimstorage.views.navigations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.shov.unlimstorage.values.Screen
import com.shov.unlimstorage.views.files.FileDescriptionScreen
import com.shov.unlimstorage.views.files.FileInfoScreen
import com.shov.unlimstorage.views.files.FilesScreen

fun NavGraphBuilder.filesComposable(filesNavController: NavController) {
	composable(route = Screen.FileInfo.route) {
		FileInfoScreen(
			navigateTo = { id ->
				filesNavController.navigate(Screen.FileDescription.setStoreItemId(id))
			},
			popBack = filesNavController::popBackStack
		)
	}
	composable(route = Screen.FileDescription.route) {
		FileDescriptionScreen(onCloseClick = filesNavController::popBackStack)
	}
	composable(route = Screen.Files.route) {
		FilesScreen(
			navigateTo = filesNavController::navigate,
			popBackStack = filesNavController::popBackStack
		)
	}
}
