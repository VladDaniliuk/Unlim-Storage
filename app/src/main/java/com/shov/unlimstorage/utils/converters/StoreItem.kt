package com.shov.unlimstorage.utils.converters

import com.box.androidsdk.content.models.BoxItem
import com.dropbox.core.v2.files.FileMetadata
import com.dropbox.core.v2.files.FolderMetadata
import com.dropbox.core.v2.files.Metadata
import com.google.api.services.drive.model.File
import com.shov.unlimstorage.models.items.ItemType
import com.shov.unlimstorage.models.items.StoreItem
import com.shov.unlimstorage.models.signInModels.StorageType
import com.shov.unlimstorage.values.ARGUMENT_METADATA
import com.shov.unlimstorage.values.UnknownClassInheritance
import javax.inject.Inject

interface StoreItemConverter {
	fun BoxItem.toStoreItem(parentFolder: String? = null): StoreItem
	fun Metadata.toStoreItem(parentFolder: String? = null): StoreItem
	fun File.toStoreItem(parentFolder: String? = null): StoreItem
}

class StoreItemConverterImpl @Inject constructor(private val sizeConverter: SizeConverter) :
	StoreItemConverter {

	override fun BoxItem.toStoreItem(parentFolder: String?) = StoreItem(
		id = this.id,
		type = ItemType.valueOf(this.type.uppercase()),
		name = this.name,
		size = when (ItemType.valueOf(this.type.uppercase())) {
			ItemType.FILE -> sizeConverter.run { this@toStoreItem.size.toBytes() }
			ItemType.FOLDER -> null
		},
		parentFolder = parentFolder?.let { this.parent.id } ?: parentFolder,
		disk = StorageType.BOX
	)

	override fun Metadata.toStoreItem(parentFolder: String?) = when (this) {
		is FolderMetadata -> StoreItem(
			id = this.id,
			type = ItemType.FOLDER,
			name = this.name,
			parentFolder = parentFolder,
			disk = StorageType.DROPBOX
		)
		is FileMetadata -> StoreItem(
			id = this.id,
			type = ItemType.FILE,
			name = this.name,
			size = sizeConverter.run { this@toStoreItem.size.toBytes() },
			parentFolder = parentFolder,
			disk = StorageType.DROPBOX
		)
		else -> throw UnknownClassInheritance(ARGUMENT_METADATA, this.javaClass.name)
	}

	override fun File.toStoreItem(parentFolder: String?) = StoreItem(
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
		parentFolder = parentFolder?.let { this.parents[0] } ?: parentFolder,
		disk = StorageType.GOOGLE
	)
}
