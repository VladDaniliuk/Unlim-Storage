package com.shov.filesfeature.views.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.shov.coreutils.values.BottomSheet
import com.shov.coreutils.values.Screen
import com.shov.filesfeature.views.FileActionsBottomSheet
import com.shov.filesfeature.views.FileDescriptionScreen
import com.shov.filesfeature.views.fileInfo.FileInfoScreen
import com.shov.filesfeature.views.files.FileListScreen
import com.shov.filesfeature.views.newObject.UploadFileBottomSheet
import com.shov.filesfeature.views.newObject.newFolder.NewFolderBottomSheet

fun NavGraphBuilder.filesComposable(navController: NavHostController) {
	composable(route = Screen.FileInfo.route) {
		FileInfoScreen(
			navigateTo = { id ->
				navController.navigate(Screen.FileDescription.setStoreItemId(id))
			},
			popBack = navController::popBackStack
		)
	}
	composable(route = Screen.FileDescription.route) {
		FileDescriptionScreen(onCloseClick = navController::popBackStack)
	}
	composable(route = Screen.Files.route) {
		FileListScreen(navHostController = navController)
	}
	@OptIn(ExperimentalMaterialNavigationApi::class)
	bottomSheet(BottomSheet.FileAction.route) {
		FileActionsBottomSheet(onNavigate = navController::navigate)
	}
	@OptIn(ExperimentalMaterialNavigationApi::class)
	bottomSheet(BottomSheet.NewFolder.route) {
		NewFolderBottomSheet(popBack = navController::popBackStack)
	}
	@OptIn(ExperimentalMaterialNavigationApi::class)
	bottomSheet(BottomSheet.UploadFile.route) {
		UploadFileBottomSheet(popBack = navController::popBackStack)
	}
}
