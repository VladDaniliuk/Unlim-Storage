package com.shov.unlimstorage.viewStates

import android.content.Context
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.UriHandler
import androidx.navigation.NavHostController
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import java.io.InputStream

data class FileInfoState(
	val clipboardManager: ClipboardManager,
	val context: Context,
	val hapticFeedback: HapticFeedback,
	val uriHandler: UriHandler
)

data class UploadNavigationState(
	var file: InputStream?,
	val folderId: String?,
	val navController: NavHostController,
	var storageType: StorageType?
)
