package com.shov.unlimstorage.models.items

import com.shov.unlimstorage.models.repositories.signIn.StorageType

data class BackStack(
	val folderId: String,
	val storageType: StorageType,
	val folderName: String
)
