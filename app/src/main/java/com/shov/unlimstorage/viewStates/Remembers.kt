package com.shov.unlimstorage.viewStates

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.platform.*
import androidx.navigation.NavController
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
fun rememberNewFolderBottomSheet(
	focusRequester: FocusRequester = remember { FocusRequester() },
	storageType: StorageType?,
	text: MutableState<String> = remember { mutableStateOf("") }
) = remember(focusRequester, storageType, text) {
	NewFolderBottomSheetState(focusRequester, mutableStateOf(storageType), text)
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
