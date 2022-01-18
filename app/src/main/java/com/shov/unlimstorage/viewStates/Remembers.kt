package com.shov.unlimstorage.viewStates

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.platform.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberFileInfoState(
	clipboardManager: ClipboardManager = LocalClipboardManager.current,
	context: Context = LocalContext.current,
	hapticFeedback: HapticFeedback = LocalHapticFeedback.current,
	uriHandler: UriHandler = LocalUriHandler.current
) = remember {
	FileInfoState(clipboardManager, context, hapticFeedback, uriHandler)
}

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
