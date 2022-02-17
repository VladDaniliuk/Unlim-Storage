package com.shov.unlimstorage.utils.converters

import android.content.Context
import com.box.androidsdk.content.models.BoxCollaborationItem
import com.dropbox.core.v2.files.FileMetadata
import com.dropbox.core.v2.files.FolderMetadata
import com.dropbox.core.v2.files.Metadata
import com.google.api.services.drive.model.File
import com.shov.coremodels.UserItem
import com.shov.unlimstorage.R
import com.shov.coremodels.StoreMetadataItem
import com.shov.coremodels.ItemType
import com.shov.unlimstorage.values.ARGUMENT_METADATA
import com.shov.unlimstorage.values.UnknownClassInheritance
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface StoreMetadataConverter {
	fun BoxCollaborationItem.toStoreMetadata(): StoreMetadataItem
	fun Metadata.toStoreMetadata(): StoreMetadataItem
	fun File.toStoreMetadata(): StoreMetadataItem
}

class StoreMetadataConverterImpl @Inject constructor(
	@ApplicationContext val context: Context
) : StoreMetadataConverter {

	override fun BoxCollaborationItem.toStoreMetadata() = StoreMetadataItem(
		id = this.id,
		name = this.name,
		type = ItemType.valueOf(this.type.uppercase()),
		description = this.description,
		isStarred = this.collections?.let { it.size > 0 } ?: false,
		version = null,
		link = this.sharedLink?.url,
		createdTime = createdAt.toPrettyTime(),
		modifiedTime = modifiedAt.toPrettyTime(),
		sharingUsers = listOf(
			UserItem(
				this.createdBy.login,
				context.getString(R.string.modifier),
				this.createdBy.name
			)
		),
		size = if (ItemType.valueOf(type.uppercase()) == ItemType.FILE) size else null
	)

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
			modifiedTime = clientModified?.time?.toPrettyTime(),
			size = size
		)
		else -> throw UnknownClassInheritance(ARGUMENT_METADATA, this.javaClass.name)
	}


	override fun File.toStoreMetadata() = StoreMetadataItem(
		id = this.id,
		name = this.name,
		type = when (this.mimeType.contains(ItemType.FOLDER.name, ignoreCase = true)) {
			true -> ItemType.FOLDER
			false -> ItemType.FILE
		},
		description = this.description,
		isStarred = this.starred,
		version = this.version.toString(),
		link = this.webViewLink,
		createdTime = createdTime.toPrettyTime(),
		modifiedTime = modifiedTime.toPrettyTime(),
		sharingUsers = this.permissions.map {
			UserItem(
				it.emailAddress,
				it.role,
				it.displayName,
				it.photoLink
			)
		},
		size = this.getSize()
	)
}
