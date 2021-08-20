package com.shov.unlimstorage.utils

import com.box.androidsdk.content.models.BoxItem
import com.dropbox.core.v2.files.FileMetadata
import com.dropbox.core.v2.files.FolderMetadata
import com.dropbox.core.v2.files.Metadata
import com.google.api.services.drive.model.File
import com.shov.unlimstorage.models.ItemType
import com.shov.unlimstorage.models.StoreItem
import com.shov.unlimstorage.models.signInModels.StorageType
import com.shov.unlimstorage.values.ARGUMENT_METADATA
import com.shov.unlimstorage.values.argumentException

fun Long.toBytes(convertType: Int = 0): String {
	val typeList = listOf("Bytes", "KB", "MB", "GB", "TB")
	var result = "$this ${typeList[convertType]}"

	if (typeList.lastIndex != convertType) {
		(this / 1024).let { newSize ->
			if (newSize > 1) {
				result = newSize.toBytes(convertType + 1)
			}
		}
	}

	return result
}

fun BoxItem.toStoreItem() = StoreItem(
	id = this.id,
	type = ItemType.valueOf(this.type.uppercase()),
	name = this.name,
	size = when (ItemType.valueOf(this.type.uppercase())) {
		ItemType.FILE -> this.size.toBytes()
		ItemType.FOLDER -> null
	},
	parentFolder = this.parent.id,
	disk = listOf(StorageType.BOX)
)

fun Metadata.toStoreItem() = when (this) {
	is FolderMetadata -> StoreItem(
		id = this.id,
		type = ItemType.FOLDER,
		name = this.name,
		size = null,
		parentFolder = this.pathLower.replace("/${this.name}", "", true),
		disk = listOf(StorageType.DROPBOX)
	)
	is FileMetadata -> StoreItem(
		id = this.id,
		type = ItemType.FILE,
		name = this.name,
		size = this.size.toBytes(),
		parentFolder = this.pathLower.replace("/${this.name}", "", true),
		disk = listOf(StorageType.DROPBOX)
	)
	else -> throw argumentException(ARGUMENT_METADATA, this.javaClass.name)
}

fun File.toStoreItem() = StoreItem(
	id = this.id,
	type = when (this.mimeType.contains(ItemType.FOLDER.name, ignoreCase = true)) {
		true -> ItemType.FOLDER
		false -> ItemType.FILE
	},
	name = this.name,
	size = when (this.mimeType.contains(ItemType.FOLDER.name, ignoreCase = true)) {
		true -> null
		false -> this.size.toLong().toBytes()
	},
	parentFolder = this.parents[0],
	disk = listOf(StorageType.GOOGLE)
)
