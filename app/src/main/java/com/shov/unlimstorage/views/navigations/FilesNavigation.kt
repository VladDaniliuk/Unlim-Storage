package com.shov.unlimstorage.views.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.shov.coreutils.values.Screen
import com.shov.filesfeature.views.FileDescriptionScreen
import com.shov.unlimstorage.views.files.FileListScreen
import com.shov.filesfeature.views.fileInfo.FileInfoScreen

fun NavGraphBuilder.filesComposable(filesNavController: NavHostController) {
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
		FileListScreen(navHostController = filesNavController)
	}
}
