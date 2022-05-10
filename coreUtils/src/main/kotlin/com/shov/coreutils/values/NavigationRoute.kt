package com.shov.coreutils.values

import com.shov.coreutils.models.BackStack

//Routes
const val navFileDescription = "nav_file_description/"
const val navFileInfo = "nav_file_info/"
const val navFiles = "nav_files/?"
const val navNewFolder = "nav_new_folder/?"
const val navUploadFile = "nav_uploadFile/?"

//Arguments
const val argFolderId = "arg_folderId"
const val argFolderName = "arg_folderName"
const val argStorageType = "arg_storageType"
const val argStoreId = "arg_storeId"

sealed class Screen(val route: String) {
	object SignIn : Screen("nav_signIn")

	//Settings routes
	object Updates : Screen("nav_updates")
	object Accounts : Screen("nav_accounts")
	object Security : Screen("nav_security")
	object Theme : Screen("nav_theme")

	//Password routes
	object CreatePassword : Screen("nav_createPassword")
	object ChangePassword : Screen("nav_changePassword")
	object CheckPassword : Screen("nav_checkPassword")
	object RemovePassword : Screen("nav_removePassword")

	object Settings : Screen("nav_settings")
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

	object Search : Screen("nav_search")
}

sealed class BottomSheet(val route: String) {
	object NewFolder : BottomSheet(
		"$navNewFolder$argStorageType={$argStorageType}/?$argFolderId={$argFolderId}"
	) {
		fun setParent(type: String?, folderId: String?) =
			"$navNewFolder$argStorageType=${type ?: ""}/?$argFolderId=${folderId ?: ""}"
	}

	object UploadFile : BottomSheet(
		"$navUploadFile$argStorageType={$argStorageType}/?$argFolderId={$argFolderId}"
	) {
		fun setParent(type: String?, folderId: String?) =
			"$navUploadFile$argStorageType=${type ?: ""}/?$argFolderId=${folderId ?: ""}"
	}

	object FileAction : BottomSheet("nav_fileAction/$argStoreId={$argStoreId}") {
		fun setStoreItemId(storeId: String) = "nav_fileAction/$argStoreId=$storeId"
	}
}

sealed class Dialog(val route: String) {
	object AddAccount : Dialog("nav_addAccount")
	object RevokeAccount : Dialog("nav_revokeAccount/{$argStorageType}") {
		fun setStorageType(storageType: String) = "nav_revokeAccount/$storageType"
	}
}
