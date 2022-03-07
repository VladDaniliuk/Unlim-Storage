package com.shov.googlestorage.converters

import com.google.api.client.util.DateTime
import com.google.api.services.drive.model.File
import com.shov.coremodels.converters.toBytes
import com.shov.coremodels.models.*
import java.text.SimpleDateFormat
import java.util.*

fun File.toStoreMetadataItem(type: ItemType): StoreMetadataItem =
	StoreMetadataItem(
		id = id,
		name = name,
		type = type,
		description = description,
		isStarred = starred,
		version = version.toString(),
		link = webViewLink,
		createdTime = createdTime.toPrettyString(),
		modifiedTime = modifiedTime.toPrettyString(),
		sharingUsers = permissions.map {
			UserItem(
				it.emailAddress,
				it.role,
				it.displayName,
				it.photoLink
			)
		},
		size = getSize()
	)

fun File.toStoreItem(folderId: String?, toSize: Pair<String, Int>.() -> String): StoreItem =
	StoreItem(
		id = id,
		type = when (mimeType.contains(ItemType.FOLDER.name, ignoreCase = true)) {
			true -> ItemType.FOLDER
			false -> ItemType.FILE
		},
		name = name,
		size = when (mimeType.contains(ItemType.FOLDER.name, ignoreCase = true)) {
			true -> null
			false -> getSize().toBytes().toSize()
		},
		parentFolder = folderId,// Need only folderId for working with room
		disk = StorageType.GOOGLE
	)

fun DateTime.toPrettyString(): String =
	SimpleDateFormat("yyyy MMMM d HH:mm", Locale.getDefault()).format(Date(value))
