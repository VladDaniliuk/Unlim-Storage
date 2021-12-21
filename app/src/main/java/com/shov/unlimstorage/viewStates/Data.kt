package com.shov.unlimstorage.viewStates

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.UriHandler
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import kotlinx.coroutines.CoroutineScope
import java.io.File

data class FileInfoState(
	val clipboardManager: ClipboardManager,
	val context: Context,
	val hapticFeedback: HapticFeedback,
	val uriHandler: UriHandler
)

data class NewFolderBottomSheetState(
	val coroutineScope: CoroutineScope,
	val focusRequester: FocusRequester,
	var storageType: MutableState<StorageType?>,
	val text: MutableState<String>
)

data class FilesScreenState(
	val navController: NavController,
	val context: Context,
	val coroutineScope: CoroutineScope
)

data class UploadNavigationState(
	val storageType: MutableState<StorageType?>,
	val navController: NavHostController,
	val file: MutableState<File?>
)
