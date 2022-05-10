package com.shov.filesfeature.views.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.shov.coreutils.values.BottomSheet
import com.shov.coreutils.values.Screen
import com.shov.filesfeature.views.FileActionsBottomSheet
import com.shov.filesfeature.views.FileDescriptionScreen
import com.shov.filesfeature.views.SearchScreen
import com.shov.filesfeature.views.fileInfo.FileInfoScreen
import com.shov.filesfeature.views.files.FileListScreen
import com.shov.filesfeature.views.newObject.UploadFileBottomSheet
import com.shov.filesfeature.views.newObject.newFolder.NewFolderBottomSheet

fun NavGraphBuilder.filesComposable() {
	composable(Screen.FileInfo.route) {
		FileInfoScreen()
	}
	composable(Screen.FileDescription.route) {
		FileDescriptionScreen()
	}
	composable(Screen.Files.route) {
		FileListScreen()
	}
	composable(Screen.Search.route) {
		SearchScreen()
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
}
