package com.shov.unlimstorage.models.items

import kotlinx.datetime.LocalDateTime

data class StoreMetadataItem(
	val id: String,
	val name: String,
	val type: ItemType,
	val description: String?,
	val isStarred: Boolean = false,
	val version: String?,
	val link: String?,
	val createdTime: LocalDateTime?,
	val modifiedTime: LocalDateTime?,
	val sharingUsers: List<User>? = null,
	val size: String?
)
