package com.shov.unlimstorage.views.navigations

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shov.unlimstorage.utils.getName
import com.shov.unlimstorage.values.BottomSheet
import com.shov.unlimstorage.viewStates.UploadNavigationState
import com.shov.unlimstorage.views.files.newFile.NewFolderBottomSheet
import com.shov.unlimstorage.views.files.newFile.NewObjectBottomSheet
import com.shov.unlimstorage.views.files.newFile.UploadBottomSheet
import java.io.FileInputStream

@Composable
fun UploadNavigation(
	uploadNavigationState: UploadNavigationState,
	context: Context = LocalContext.current
) {
	NavHost(
		navController = uploadNavigationState.navController,
		startDestination = BottomSheet.Main.route
	) {
		composable(BottomSheet.Main.route) {
			NewObjectBottomSheet(
				onFolderCreateClick = {
					uploadNavigationState.navController.navigate(
						BottomSheet.NewFolder.setParent(
							uploadNavigationState.storageType?.name,
							uploadNavigationState.folderId
						)
					)
				}
			) { fileUri ->
				fileUri?.let { uri ->
					uploadNavigationState.file =
						context.contentResolver.openFileDescriptor(uri, "r", null)

					uploadNavigationState.navController.navigate(
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
			UploadBottomSheet(FileInputStream(uploadNavigationState.file?.fileDescriptor))
		}
	}

	DisposableEffect(key1 = null) {
		onDispose {
			uploadNavigationState.file?.close()
		}
	}
}
