package com.shov.unlimstorage.viewStates

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.platform.*
import com.shov.unlimstorage.models.repositories.signIn.StorageType


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
