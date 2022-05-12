package com.shov.filesfeature.views.navigations

import androidx.navigation.NavGraphBuilder
import com.shov.coreui.ui.bottomSheetComposable
import com.shov.coreui.ui.horizontalComposable
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
	horizontalComposable(Screen.FileInfo.route) {
		FileInfoScreen()
	}
	horizontalComposable(Screen.FileDescription.route) {
		FileDescriptionScreen()
	}
	horizontalComposable(Screen.Files.route) {
		FileListScreen()
	}
	horizontalComposable(Screen.Search.route) {
		SearchScreen()
	}
	bottomSheetComposable(BottomSheet.FileAction.route) {
		FileActionsBottomSheet()
	}
	bottomSheetComposable(BottomSheet.NewFolder.route) {
		NewFolderBottomSheet()
	}
	bottomSheetComposable(BottomSheet.UploadFile.route) {
		UploadFileBottomSheet()
	}
}
