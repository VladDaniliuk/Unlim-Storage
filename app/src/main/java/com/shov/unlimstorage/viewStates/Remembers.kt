package com.shov.unlimstorage.viewStates

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.platform.*
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import kotlinx.coroutines.CoroutineScope
import java.io.File


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
fun rememberNewFolderBottomSheet(
	coroutineScope: CoroutineScope = rememberCoroutineScope(),
	focusRequester: FocusRequester = remember { FocusRequester() },
	storageType: StorageType?,
	text: MutableState<String> = remember { mutableStateOf("") }
) = remember(focusRequester, storageType, text) {
	NewFolderBottomSheetState(coroutineScope, focusRequester, mutableStateOf(storageType), text)
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
	type: StorageType?,
	storageType: MutableState<StorageType?> = mutableStateOf(type),
	navController: NavHostController = rememberNavController()
) = remember(storageType.value, navController, mutableStateOf<File?>(null)) {
	UploadNavigationState(storageType, navController, mutableStateOf(null))
}
