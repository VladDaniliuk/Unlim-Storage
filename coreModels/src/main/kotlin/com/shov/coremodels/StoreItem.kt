package com.shov.coremodels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StoreItem(
	@PrimaryKey val id: String,
	@ColumnInfo val type: ItemType,
	@ColumnInfo val name: String,
	@ColumnInfo val disk: StorageType,
	@ColumnInfo val size: String? = null,
	@ColumnInfo val parentFolder: String? = null
)
