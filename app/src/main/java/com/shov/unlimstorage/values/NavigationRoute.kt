package com.shov.unlimstorage.values

//Arguments
const val argFolderId = "arg_folderId"
const val argStorageType = "arg_storageType"

//Navigation
const val navAccounts = "nav_accounts"
const val navSettings = "nav_settings"
const val navSignIn = "nav_signIn"
const val navMain = "nav_main"

fun navFiles(folderId: String = "{$argFolderId}", storageType: String? = "{$argStorageType}") =
	"nav_main/?$argFolderId=$folderId&$argStorageType=$storageType"
