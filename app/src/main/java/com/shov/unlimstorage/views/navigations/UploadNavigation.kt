package com.shov.unlimstorage.views.navigations

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shov.coreutils.values.BottomSheet
import com.shov.filesfeature.views.newObject.NewFolderBottomSheet
import com.shov.filesfeature.views.newObject.NewObjectBottomSheet
import com.shov.filesfeature.views.newObject.UploadBottomSheet
import com.shov.unlimstorage.utils.getName
import com.shov.unlimstorage.viewModels.navigations.UploadNavigationViewModel
import com.shov.unlimstorage.viewStates.UploadNavigationState

@Composable
fun UploadNavigation(
	context: Context = LocalContext.current,
	navController: NavHostController = rememberNavController(),
	uploadNavigationState: UploadNavigationState,
	uploadNavigationViewModel: UploadNavigationViewModel = hiltViewModel(),
) {
	NavHost(
		navController = navController,
		startDestination = BottomSheet.Main.route
	) {
		composable(BottomSheet.Main.route) {
			NewObjectBottomSheet(
				onFolderCreateClick = {
					navController.navigate(
						BottomSheet.NewFolder.setParent(
							uploadNavigationState.storageType?.name,
							uploadNavigationState.folderId
						)
					)
				}
			) { fileUri ->
				fileUri?.let { uri ->
					uploadNavigationViewModel.setFileDescriptor(
						context.contentResolver.openFileDescriptor(uri, "r", null)
					)

					navController.navigate(
						BottomSheet.ChooseFile.setParent(
							uploadNavigationState.storageType?.name,
							uploadNavigationState.folderId,
							context.contentResolver.getName(uri)
						)
					)
				}
			}
		}
		composable(BottomSheet.NewFolder.route) {
			NewFolderBottomSheet()
		}
		composable(BottomSheet.ChooseFile.route) {
			UploadBottomSheet(uploadNavigationViewModel.inputStream)
		}
	}

	DisposableEffect(key1 = null) {
		onDispose(uploadNavigationViewModel::closeFileDescriptor)
	}
}
