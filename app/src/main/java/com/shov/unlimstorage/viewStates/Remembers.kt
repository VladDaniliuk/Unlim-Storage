package com.shov.unlimstorage.viewStates

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.platform.*


@Composable
fun rememberFileInfoState(
	clipboardManager: ClipboardManager = LocalClipboardManager.current,
	context: Context = LocalContext.current,
	hapticFeedback: HapticFeedback = LocalHapticFeedback.current,
	uriHandler: UriHandler = LocalUriHandler.current
) = remember {
	FileInfoState(clipboardManager, context, hapticFeedback, uriHandler)
}
