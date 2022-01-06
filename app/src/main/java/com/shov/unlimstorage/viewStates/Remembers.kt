package com.shov.unlimstorage.viewStates

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.platform.*
import androidx.navigation.NavController
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
	navController: NavController,
	context: Context = LocalContext.current,
	coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(navController) {
	FilesScreenState(
		navController,
		context,
		coroutineScope
	)
}

@Composable
fun rememberUploadNavigationState(
	folderId: String?,
	navController: NavHostController = rememberNavController(),
	storageType: StorageType?
) = remember(navController) {
	UploadNavigationState(null, folderId, navController, storageType)
}
