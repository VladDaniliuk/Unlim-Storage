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
) {
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as StoreItem

		if (id != other.id) return false
		if (type != other.type) return false
		if (name != other.name) return false
		if (size != other.size) return false

		return true
	}

	override fun hashCode(): Int {
		var result = type.hashCode()
		result = 31 * result + id.hashCode()
		result = 31 * result + name.hashCode()
		result = 31 * result + size.hashCode()
		return result
	}
}
