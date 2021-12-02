package com.shov.unlimstorage.values

//Main routes
const val navFiles = "nav_files/?"
const val navFileInfo = "nav_file_info/"
const val navFileDescription = "nav_file_description/"

//Arguments
const val argFolderId = "arg_folderId"
const val argStorageType = "arg_storageType"
const val argStoreId = "arg_storeId"

sealed class Screen(val route: String) {
	object SignIn : Screen("nav_signIn")
	object Accounts : Screen("nav_accounts")
	object Settings : Screen("nav_settings")
	object Updates : Screen("nav_updates")
	object Files :
		Screen("$navFiles$argFolderId={$argFolderId}/?$argStorageType={$argStorageType}") {
		fun openFolder(folderId: String, storageType: String) =
			"$navFiles$argFolderId=$folderId/?$argStorageType=$storageType"
	}

	object FileInfo : Screen("$navFileInfo{$argStoreId}") {
		fun setStoreItem(storeId: String) = "$navFileInfo$storeId"
	}

	object FileDescription : Screen("$navFileDescription{$argStoreId}") {
		fun setStoreItemId(storeId: String) =
			"$navFileDescription$storeId"
	}
}
