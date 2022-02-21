package com.shov.unlimstorage.viewStates

import android.content.Context
import androidx.navigation.NavHostController
import com.shov.coremodels.models.StorageType
import kotlinx.coroutines.CoroutineScope
import java.io.InputStream

data class FilesScreenState(
	val navHostController: NavHostController,
	val context: Context,
	val coroutineScope: CoroutineScope
)

data class UploadNavigationState(
	var file: InputStream?,
	val folderId: String?,
	val navController: NavHostController,
	var storageType: StorageType?
)
