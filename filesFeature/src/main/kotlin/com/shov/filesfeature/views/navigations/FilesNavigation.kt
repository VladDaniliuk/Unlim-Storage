package com.shov.filesfeature.views.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.shov.coreutils.values.BottomSheet
import com.shov.coreutils.values.Screen
import com.shov.filesfeature.views.FileDescriptionScreen
import com.shov.filesfeature.views.fileActions.FileActionsBottomSheet
import com.shov.filesfeature.views.fileActions.FileRenameBottomSheet
import com.shov.filesfeature.views.fileInfo.FileInfoScreen
import com.shov.filesfeature.views.files.FileListScreen
import com.shov.filesfeature.views.newObject.UploadFileBottomSheet
import com.shov.filesfeature.views.newObject.newFolder.NewFolderBottomSheet

fun NavGraphBuilder.filesComposable() {
	composable(route = Screen.FileInfo.route) {
		FileInfoScreen()
	}
	composable(route = Screen.FileDescription.route) {
		FileDescriptionScreen()
	}
	composable(route = Screen.Files.route) {
		FileListScreen()
	}
	@OptIn(ExperimentalMaterialNavigationApi::class)
	bottomSheet(BottomSheet.FileAction.route) {
		FileActionsBottomSheet()
	}
	@OptIn(ExperimentalMaterialNavigationApi::class)
	bottomSheet(BottomSheet.NewFolder.route) {
		NewFolderBottomSheet()
	}
	@OptIn(ExperimentalMaterialNavigationApi::class)
	bottomSheet(BottomSheet.UploadFile.route) {
		UploadFileBottomSheet()
	}

	@OptIn(ExperimentalMaterialNavigationApi::class)
	bottomSheet(BottomSheet.RenameFile.route) {
		FileRenameBottomSheet()
	}
}
