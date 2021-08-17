package com.shov.unlimstorage.models

import com.box.androidsdk.content.models.BoxItem
import com.shov.unlimstorage.models.signInModels.StorageType

data class StoreItem(
	val id: String,
	val type: String,
	val name: String,
	val size: Long,
	val disk: Array<StorageType>
) {
	constructor(boxItem: BoxItem) : this(
		id = boxItem.id,
		type = boxItem.type,
		name = boxItem.name,
		size = boxItem.size,
		disk = arrayOf(StorageType.BOX)
	)

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
