package com.shov.unlimstorage.values

//Arguments
const val argFolderId = "arg_folderId"
const val argStorageType = "arg_storageType"
const val argStoreItem = "arg_storeItem"
const val argStoreMetadata = "arg_storeMetadata"

//Navigation //TODO To sealed class
const val navAccounts = "nav_accounts"
const val navFileDescription = "nav_fileDescription"
const val navFileInfo = "nav_fileInfo"
const val navSettings = "nav_settings"
const val navSignIn = "nav_signIn"
const val navMain = "nav_main"
const val navUpdates = "nav_updates"

fun navFiles(folderId: String = "{$argFolderId}", storageType: String = "{$argStorageType}") =
	"$navMain/?$argFolderId=$folderId&$argStorageType=$storageType"
