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
			filesNavController = filesNavController,
			scaffoldState = scaffoldState,
		)
	}
	composable(route = Screen.FileDescription.route) {
		FileDescriptionScreen(
			onCloseClick = filesNavController::popBackStack,
			onDoneClick = {
				scaffoldState.snackbarHostState.showSnackbar("Doesn't work now")
			}
		)
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
					sheetContent.value = null
				}
			}
		)
	}
}
