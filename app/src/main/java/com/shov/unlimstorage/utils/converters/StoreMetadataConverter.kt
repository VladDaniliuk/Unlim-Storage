package com.shov.unlimstorage.utils.converters

import android.content.Context
import com.dropbox.core.v2.files.FileMetadata
import com.dropbox.core.v2.files.FolderMetadata
import com.dropbox.core.v2.files.Metadata
import com.shov.coremodels.models.ItemType
import com.shov.coremodels.models.StoreMetadataItem
import com.shov.unlimstorage.values.ARGUMENT_METADATA
import com.shov.unlimstorage.values.UnknownClassInheritance
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface StoreMetadataConverter {
	fun Metadata.toStoreMetadata(): StoreMetadataItem
}

class StoreMetadataConverterImpl @Inject constructor(
	@ApplicationContext val context: Context
) : StoreMetadataConverter {
	override fun Metadata.toStoreMetadata(): StoreMetadataItem = when (this) {
		is FolderMetadata -> StoreMetadataItem(
			id = this.id,
			name = this.name,
			type = ItemType.FOLDER,
			isStarred = false,
		)
		is FileMetadata -> StoreMetadataItem(
			id = this.id,
			name = this.name,
			type = ItemType.FILE,
			description = null,
			isStarred = false,
			version = null,
			link = null,
			createdTime = fileLockInfo?.created?.toPrettyTime(),
			modifiedTime = clientModified?.toPrettyTime(),
			size = size
		)
		else -> throw UnknownClassInheritance(ARGUMENT_METADATA, this.javaClass.name)
	}
}
