package com.shov.unlimstorage.utils.converters

import android.content.Context
import com.box.androidsdk.content.models.BoxItem
import com.dropbox.core.v2.files.FileMetadata
import com.dropbox.core.v2.files.FolderMetadata
import com.dropbox.core.v2.files.Metadata
import com.google.api.services.drive.model.File
import com.shov.unlimstorage.values.ARGUMENT_METADATA
import com.shov.coremodels.models.ItemType
import com.shov.coremodels.models.StoreItem
import com.shov.coremodels.models.StorageType
import com.shov.unlimstorage.values.UnknownClassInheritance
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface StoreItemConverter {
	fun BoxItem.toStoreItem(parentFolder: String?): StoreItem
	fun Metadata.toStoreItem(parentFolder: String?): StoreItem
	fun File.toStoreItem(parentFolder: String?): StoreItem
}

class StoreItemConverterImpl @Inject constructor(@ApplicationContext val context: Context) :
	StoreItemConverter {
	override fun BoxItem.toStoreItem(parentFolder: String?) = StoreItem(
		id = this.id,
		type = ItemType.valueOf(this.type.uppercase()),
		name = this.name,
		size = when (ItemType.valueOf(this.type.uppercase())) {
			ItemType.FILE -> size.toBytes().let { context.getString(it.second, it.first) }
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
			size = size.toBytes().let { context.getString(it.second, it.first) },
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
		size = when (mimeType.contains(ItemType.FOLDER.name, ignoreCase = true)) {
			true -> null
			false -> getSize().toBytes().let { context.getString(it.second, it.first) }
		},
		parentFolder = parentFolder?.let { this.parents[0] } ?: parentFolder,
		disk = StorageType.GOOGLE
	)
}
