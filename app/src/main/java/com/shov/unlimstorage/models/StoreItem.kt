package com.shov.unlimstorage.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shov.unlimstorage.models.signInModels.StorageType

enum class ItemType {
	FILE, FOLDER
}

@Entity
data class StoreItem(
	@PrimaryKey val id: String,
	@ColumnInfo val type: ItemType,
	@ColumnInfo val name: String,
	@ColumnInfo val size: String? = null,
	@ColumnInfo val parentFolder: String? = null,
	@ColumnInfo val disk: StorageType
)
