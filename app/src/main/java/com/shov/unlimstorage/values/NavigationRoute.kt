package com.shov.unlimstorage.values

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
		Screen("nav_files/?$argFolderId={$argFolderId}/?$argStorageType={$argStorageType}") {
		fun openFolder(folderId: String, storageType: String) =
			"nav_files/?$argFolderId=$folderId/?$argStorageType=$storageType"
	}

	object FileInfo : Screen("nav_file_info/{$argStoreId}") {
		fun setStoreItem(storeId: String) = "nav_file_info/$storeId"
	}

	object FileDescription : Screen("nav_file_description/{$argStoreId}") {
		fun setStoreItemId(storeId: String) =
			"nav_file_description/$storeId"
	}
}
