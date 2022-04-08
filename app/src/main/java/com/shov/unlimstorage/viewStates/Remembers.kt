package com.shov.unlimstorage.viewStates

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.shov.coremodels.models.StorageType

@Composable
fun rememberUploadNavigationState(
	folderId: String?,
	navController: NavHostController = rememberNavController(),
	storageType: StorageType?
) = remember {
	UploadNavigationState(null, folderId, navController, storageType)
}
