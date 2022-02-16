package com.shov.unlimstorage.viewStates

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberFilesScreenState(
	navHostController: NavHostController,
	context: Context = LocalContext.current,
	coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember {
	FilesScreenState(navHostController, context, coroutineScope)
}

@Composable
fun rememberUploadNavigationState(
	folderId: String?,
	navController: NavHostController = rememberNavController(),
	storageType: StorageType?
) = remember {
	UploadNavigationState(null, folderId, navController, storageType)
}
