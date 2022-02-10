package com.shov.unlimstorage.models.items

data class StoreMetadataItem(
	val id: String,
	val name: String,
	val type: ItemType,
	var description: String? = null,
	val isStarred: Boolean = false,
	val version: String? = null,
	val link: String? = null,
	val createdTime: String? = null,
	val modifiedTime: String? = null,
	val sharingUsers: List<User> = emptyList(),
	val size: Long? = null
)
