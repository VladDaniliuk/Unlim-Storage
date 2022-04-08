package com.shov.unlimstorage.views.navigations

import android.content.Context
import android.os.ParcelFileDescriptor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shov.coreutils.values.BottomSheet
import com.shov.unlimstorage.utils.getName
import com.shov.unlimstorage.viewStates.UploadNavigationState
import com.shov.filesfeature.views.newObject.NewObjectBottomSheet
import com.shov.filesfeature.views.newObject.UploadBottomSheet
import com.shov.filesfeature.views.newObject.NewFolderBottomSheet
import java.io.FileInputStream

@Composable
fun UploadNavigation(
	uploadNavigationState: UploadNavigationState,
	context: Context = LocalContext.current
) {
	var fileDescriptor: ParcelFileDescriptor? = null

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
					fileDescriptor = context.contentResolver.openFileDescriptor(uri, "r", null)

					uploadNavigationState.file = FileInputStream(fileDescriptor?.fileDescriptor)

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
			UploadBottomSheet(uploadNavigationState.file)
		}
	}

	DisposableEffect(key1 = null) {
		onDispose {
			fileDescriptor?.close()
		}
	}
}
