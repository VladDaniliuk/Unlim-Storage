package com.shov.unlimstorage.utils

import androidx.navigation.NavHostController
import com.shov.unlimstorage.models.items.BackStack
import com.shov.unlimstorage.values.argFolderId
import com.shov.unlimstorage.values.argFolderName
import com.shov.unlimstorage.values.argStorageType

fun NavHostController.getBackStack() = currentBackStackEntry?.arguments?.run {
	BackStack(
		getString(argFolderId) ?: throw IllegalArgumentException("No folder id"),
		getString(argStorageType) ?: throw IllegalArgumentException("No storage type"),
		getString(argFolderName) ?: throw IllegalArgumentException("No folder name")
	)
}
