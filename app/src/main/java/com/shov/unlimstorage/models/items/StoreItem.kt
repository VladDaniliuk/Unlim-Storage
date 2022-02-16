package com.shov.unlimstorage.models.items

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shov.unlimstorage.models.repositories.signIn.StorageType

enum class ItemType(val imageVector: ImageVector) {
	FILE(Icons.Rounded.Description), FOLDER(Icons.Rounded.Folder);
}

@Entity
data class StoreItem(
	@PrimaryKey val id: String,
	@ColumnInfo val type: ItemType,
	@ColumnInfo val name: String,
	@ColumnInfo val disk: StorageType,
	@ColumnInfo val size: String? = null,
	@ColumnInfo val parentFolder: String? = null
)
