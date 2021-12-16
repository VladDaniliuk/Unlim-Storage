package com.shov.unlimstorage.viewStates

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.UriHandler
import com.shov.unlimstorage.models.repositories.signIn.StorageType

data class FileInfoState(
	val clipboardManager: ClipboardManager,
	val context: Context,
	val hapticFeedback: HapticFeedback,
	val uriHandler: UriHandler
)

data class NewFolderBottomSheetState(
	val focusRequester: FocusRequester,
	var storageType: MutableState<StorageType?>,
	val text: MutableState<String>
)
