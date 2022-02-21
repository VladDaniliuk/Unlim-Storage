package com.shov.coremodels.models

data class StoreMetadataItem(
	val id: String,
	val name: String,
	val type: ItemType,
	var description: String? = "",
	val isStarred: Boolean = false,
	val version: String? = null,
	val link: String? = null,
	val createdTime: String? = null,
	val modifiedTime: String? = null,
	val sharingUsers: List<UserItem> = emptyList(),
	val size: Long? = null
)
