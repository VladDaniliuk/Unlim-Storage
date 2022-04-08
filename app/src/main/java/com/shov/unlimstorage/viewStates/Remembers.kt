package com.shov.unlimstorage.viewStates

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.shov.coremodels.models.StorageType

@Composable
fun rememberUploadNavigationState(
	folderId: String?,
	storageType: StorageType?
) = remember {
	UploadNavigationState(folderId, storageType)
}
