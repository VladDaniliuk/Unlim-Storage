package com.shov.unlimstorage.values

import com.shov.unlimstorage.models.items.BackStack

//Main routes
const val navFiles = "nav_files/?"
const val navFileInfo = "nav_file_info/"
const val navFileDescription = "nav_file_description/"
const val navNewFolder = "nav_new_folder/?"
const val navChooseFile = "nav_choose_file/?"

//Arguments
const val argFolderId = "arg_folderId"
const val argFolderName = "arg_folderName"
const val argStorageType = "arg_storageType"
const val argStoreId = "arg_storeId"
const val argFileName = "arg_fileName"

sealed class Screen(val route: String) {
	object SignIn : Screen("nav_signIn")
	object Accounts : Screen("nav_accounts")
	object Settings : Screen("nav_settings")
	object Updates : Screen("nav_updates")
	object Files : Screen(
		"$navFiles$argFolderId={$argFolderId}/?$argStorageType={$argStorageType}/?" +
				"$argFolderName={$argFolderName}"
	) {
		fun openFolder(backStack: BackStack) = "$navFiles$argFolderId=${backStack.folderId}" +
				"/?$argStorageType=${backStack.storageType}/?$argFolderName=${backStack.folderName}"
	}

	object FileInfo : Screen("$navFileInfo{$argStoreId}") {
		fun setStoreItem(storeId: String) = "$navFileInfo$storeId"
	}

	object FileDescription : Screen("$navFileDescription{$argStoreId}") {
		fun setStoreItemId(storeId: String) = "$navFileDescription$storeId"
	}
}

sealed class BottomSheet(val route: String) {
	object ChooseFile : BottomSheet(
		"$navChooseFile$argStorageType={$argStorageType}/?$argFolderId={$argFolderId}/?" +
				"$argFileName={$argFileName}"
	) {
		fun setParent(type: String?, folderId: String?, fileName: String) =
			"$navChooseFile$argStorageType=${type ?: "{$argStorageType}"}/?" +
					"$argFolderId=${folderId ?: "{$argFolderId}"}/?" +
					"$argFileName=${fileName}"
	}

	object Main : BottomSheet("nav_main")
	object NewFolder : BottomSheet(
		"$navNewFolder$argStorageType={$argStorageType}/?$argFolderId={$argFolderId}"
	) {
		fun setParent(type: String?, folderId: String?) =
			"$navNewFolder$argStorageType=${type ?: "{$argStorageType}"}/?" +
					"$argFolderId=${folderId ?: "{$argFolderId}"}"
	}
}
