package com.shov.unlimstorage.views.navigations

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.shov.unlimstorage.values.Screen
import com.shov.unlimstorage.views.files.FileDescriptionScreen
import com.shov.unlimstorage.views.files.FileInfoScreen
import com.shov.unlimstorage.views.files.FilesScreen

@OptIn(ExperimentalMaterialApi::class)
fun NavGraphBuilder.filesComposable(
	filesNavController: NavController,
	scaffoldState: ScaffoldState,
	sheetContent: MutableState<(@Composable ColumnScope.() -> Unit)?>,
	sheetState: ModalBottomSheetState
) {
	composable(route = Screen.FileInfo.route) {
		FileInfoScreen(
			navigateTo = { id ->
				filesNavController.navigate(Screen.FileDescription.setStoreItemId(id))
			},
			popBack= filesNavController::popBackStack
		)
	}
	composable(route = Screen.FileDescription.route) {
		FileDescriptionScreen(onCloseClick = filesNavController::popBackStack)
	}
	composable(route = Screen.Files.route) {
		FilesScreen(
			scaffoldState = scaffoldState,
			filesNavController = filesNavController,
			sheetContent = sheetContent,
			onShowSheet = { isShow ->
				if (isShow) {
					sheetState.show()
				} else {
					sheetState.hide()
				}
			}
		)
	}
}
