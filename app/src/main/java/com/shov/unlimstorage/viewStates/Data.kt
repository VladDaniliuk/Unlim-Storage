package com.shov.unlimstorage.viewStates

import android.content.Context
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.UriHandler

data class FileInfoState(
	val clipboardManager: ClipboardManager,
	val context: Context,
	val hapticFeedback: HapticFeedback,
	val uriHandler: UriHandler
)
