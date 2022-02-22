package com.shov.unlimstorage.utils.converters

import android.content.Context
import com.dropbox.core.v2.files.FileMetadata
import com.dropbox.core.v2.files.FolderMetadata
import com.dropbox.core.v2.files.Metadata
import com.shov.coremodels.converters.toBytes
import com.shov.coremodels.models.ItemType
import com.shov.coremodels.models.StorageType
import com.shov.coremodels.models.StoreItem
import com.shov.unlimstorage.values.ARGUMENT_METADATA
import com.shov.unlimstorage.values.UnknownClassInheritance
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface StoreItemConverter {
	fun Metadata.toStoreItem(parentFolder: String?): StoreItem
}

class StoreItemConverterImpl @Inject constructor(@ApplicationContext val context: Context) :
	StoreItemConverter {

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
}
