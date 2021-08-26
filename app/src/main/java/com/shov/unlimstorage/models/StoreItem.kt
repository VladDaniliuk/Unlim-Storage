package com.shov.unlimstorage.models

import com.shov.unlimstorage.models.signInModels.StorageType

enum class ItemType {
	FILE, FOLDER
}

data class StoreItem(
	val id: String,
	val type: ItemType,
	val name: String,
	val size: String?,
	val parentFolder: String?,
	val disk: StorageType
)
