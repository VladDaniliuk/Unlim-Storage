package com.shov.unlimstorage.views.navigations

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shov.unlimstorage.values.BottomSheet
import com.shov.unlimstorage.viewStates.UploadNavigationState
import com.shov.unlimstorage.viewStates.rememberNewFolderBottomSheet
import com.shov.unlimstorage.views.files.newFile.ChooseDriveBottomSheet
import com.shov.unlimstorage.views.files.newFile.NewFolderBottomSheet
import com.shov.unlimstorage.views.files.newFile.UploadBottomSheet

@Composable
fun UploadNavigation(uploadNavigationState: UploadNavigationState) {
	NavHost(
		navController = uploadNavigationState.navController,
		startDestination = BottomSheet.Main.route
	) {
		composable(BottomSheet.Main.route) {
			UploadBottomSheet(
				onFolderCreateClick = {
					uploadNavigationState.navController.navigate(BottomSheet.NewFolder.route)
				}
			) { uri ->
				uploadNavigationState.file.value = uri
				uploadNavigationState.navController.navigate(BottomSheet.ChooseFile.route)
			}
		}
		composable(BottomSheet.NewFolder.route) {
			NewFolderBottomSheet(
				rememberNewFolderBottomSheet(storageType = uploadNavigationState.storageType.value)
			)
		}
		composable(BottomSheet.ChooseFile.route) {
			if (uploadNavigationState.storageType.value != null) {
				Column {
					uploadNavigationState.file.value?.let { file ->
						Text(text = file.name)
						Text(text = file.path)
						Text(text = file.absolutePath)
					}
				}
			} else {
				ChooseDriveBottomSheet { storageType ->
					uploadNavigationState.storageType.value = storageType
				}
			}
		}
	}
}
