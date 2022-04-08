package com.shov.filesfeature.views.newObject

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
import com.shov.filesfeature.utils.getName
import com.shov.filesfeature.viewModels.newObject.UploadNavigationViewModel
import com.shov.filesfeature.views.newObject.newFile.UploadBottomSheet
import com.shov.filesfeature.views.newObject.newFolder.NewFolderBottomSheet

@Composable
fun NewObjectBottomSheetNavigation(
	context: Context = LocalContext.current,
	folderId: String?,
	navController: NavHostController = rememberNavController(),
	storageType: String?,
	uploadNavigationViewModel: UploadNavigationViewModel = hiltViewModel(),
) {
	NavHost(
		navController = navController,
		startDestination = BottomSheet.Main.route
	) {
		composable(BottomSheet.Main.route) {
			NewObjectBottomSheet(
				onFolderCreateClick = {
					navController.navigate(BottomSheet.NewFolder.setParent(storageType, folderId))
				}
			) { fileUri ->
				fileUri?.let { uri ->
					uploadNavigationViewModel.setFileDescriptor(
						context.contentResolver.openFileDescriptor(uri, "r", null)
					)

					navController.navigate(
						BottomSheet.ChooseFile.setParent(
							storageType,
							folderId,
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
