package com.shov.unlimstorage.views.navigations

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import com.shov.unlimstorage.values.Screen
import com.shov.unlimstorage.values.argFolderId
import com.shov.unlimstorage.values.argStorageType
import com.shov.unlimstorage.values.argStoreId
import com.shov.unlimstorage.viewModels.fileDescriptionViewModel
import com.shov.unlimstorage.viewModels.fileInfoViewModel
import com.shov.unlimstorage.viewModels.singletonViewModel
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
	composable(
		route = Screen.FileInfo.route,
		arguments = listOf(navArgument(argStoreId) {})
	) { navBackStackEntry ->
		navBackStackEntry.arguments?.getString(argStoreId)?.let { id ->
			FileInfoScreen(
				fileInfoViewModel = fileInfoViewModel(id),
				filesNavController = filesNavController,
				scaffoldState = scaffoldState,
				topAppBarViewModel = singletonViewModel()
			)
		}
	}
	composable(
		route = Screen.FileDescription.route,
		arguments = listOf(navArgument(argStoreId) {})
	) { navBackStackEntry ->
		navBackStackEntry.arguments?.apply {
			getString(argStoreId)?.let { id ->
				FileDescriptionScreen(
					filesNavController = filesNavController,
					fileDescriptionViewModel = fileDescriptionViewModel(id),
					topAppBarViewModel = singletonViewModel(),
					scaffoldState = scaffoldState
				)
			}
		}
	}
	composable(
		arguments = listOf(
			navArgument(argFolderId) { nullable = true },
			navArgument(argStorageType) { nullable = true }
		),
		route = Screen.Files.route
	) { navBackStackEntry ->
		navBackStackEntry.arguments?.let { arguments ->
			FilesScreen(
				scaffoldState = scaffoldState,
				filesNavController = filesNavController,
				filesViewModel = hiltViewModel(),
				folderId = arguments.getString(argFolderId),
				topAppBarViewModel = singletonViewModel(),
				storageType = arguments.getString(argStorageType)
					?.let { storageType ->
						StorageType.valueOf(storageType)
					},
				sheetContent = sheetContent,
				sheetState = sheetState
			)
		}
	}
}
