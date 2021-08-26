package com.shov.unlimstorage.utils.converters

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
import javax.inject.Inject

interface StoreItemConverter {
	fun BoxItem.toStoreItem(): StoreItem
	fun Metadata.toStoreItem(): StoreItem
	fun File.toStoreItem(): StoreItem
}

class StoreItemConverterImpl @Inject constructor(private val sizeConverter: SizeConverter) :
	StoreItemConverter {

	override fun BoxItem.toStoreItem() = StoreItem(
		id = this.id,
		type = ItemType.valueOf(this.type.uppercase()),
		name = this.name,
		size = when (ItemType.valueOf(this.type.uppercase())) {
			ItemType.FILE -> sizeConverter.run { this@toStoreItem.size.toBytes() }
			ItemType.FOLDER -> null
		},
		parentFolder = this.parent.id,
		disk = StorageType.BOX
	)

	override fun Metadata.toStoreItem() = when (this) {
		is FolderMetadata -> StoreItem(
			id = this.id,
			type = ItemType.FOLDER,
			name = this.name,
			size = null,
			parentFolder = this.pathLower.replace("/${this.name}", "", true),
			disk = StorageType.DROPBOX
		)
		is FileMetadata -> StoreItem(
			id = this.id,
			type = ItemType.FILE,
			name = this.name,
			size = sizeConverter.run { this@toStoreItem.size.toBytes() },
			parentFolder = this.pathLower.replace("/${this.name}", "", true),
			disk = StorageType.DROPBOX
		)
		else -> throw argumentException(ARGUMENT_METADATA, this.javaClass.name)
	}

	override fun File.toStoreItem() = StoreItem(
		id = this.id,
		type = when (this.mimeType.contains(ItemType.FOLDER.name, ignoreCase = true)) {
			true -> ItemType.FOLDER
			false -> ItemType.FILE
		},
		name = this.name,
		size = when (this.mimeType.contains(ItemType.FOLDER.name, ignoreCase = true)) {
			true -> null
			false -> sizeConverter.run { this@toStoreItem.size.toLong().toBytes() }
		},
		parentFolder = this.parents[0],
		disk = StorageType.GOOGLE
	)
}
