package com.shov.unlimstorage.utils

import androidx.navigation.NavHostController
import com.shov.coreutils.models.BackStack
import com.shov.coreutils.values.argFolderId
import com.shov.coreutils.values.argFolderName
import com.shov.coreutils.values.argStorageType

fun NavHostController.popBackStack(size: Int) {
	for (i in 0 until size) popBackStack()
}

fun NavHostController.getBackStack() = currentBackStackEntry?.arguments?.run {
	BackStack(
		getString(argFolderId) ?: throw IllegalArgumentException("No folder id"),
		getString(argStorageType) ?: throw IllegalArgumentException("No storage type"),
		getString(argFolderName) ?: throw IllegalArgumentException("No folder name")
	)
}

fun NavHostController.navigateTo(route: String) = navigate(route)
