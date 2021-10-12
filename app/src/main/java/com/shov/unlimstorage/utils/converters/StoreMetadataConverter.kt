package com.shov.unlimstorage.utils.converters

import android.content.Context
import com.box.androidsdk.content.models.BoxCollaborationItem
import com.dropbox.core.v2.files.FileMetadata
import com.dropbox.core.v2.files.FolderMetadata
import com.dropbox.core.v2.files.Metadata
import com.google.api.services.drive.model.File
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.items.ItemType
import com.shov.unlimstorage.models.items.StoreMetadataItem
import com.shov.unlimstorage.models.items.User
import com.shov.unlimstorage.values.ARGUMENT_METADATA
import com.shov.unlimstorage.values.UnknownClassInheritance
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

interface StoreMetadataConverter {
	fun BoxCollaborationItem.toStoreMetadata(): StoreMetadataItem
	fun Metadata.toStoreMetadata(): StoreMetadataItem
	fun File.toStoreMetadata(): StoreMetadataItem
}

class StoreMetadataConverterImpl @Inject constructor(
	private val sizeConverter: SizeConverter,
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
		createdTime = Instant.fromEpochMilliseconds(this.createdAt.time)
			.toLocalDateTime(TimeZone.currentSystemDefault()),
		modifiedTime = Instant.fromEpochMilliseconds(this.modifiedAt.time)
			.toLocalDateTime(TimeZone.currentSystemDefault()),
		sharingUsers = listOf(
			User(
				this.createdBy.login,
				context.getString(R.string.modifier),
				this.createdBy.name
			)
		),
		size = when (ItemType.valueOf(this@toStoreMetadata.type.uppercase())) {
			ItemType.FILE -> sizeConverter.run { this@toStoreMetadata.size.toBytes() }
			ItemType.FOLDER -> null
		}
	)

	override fun Metadata.toStoreMetadata(): StoreMetadataItem = when (this) {
		is FolderMetadata -> StoreMetadataItem(
			id = this.id,
			name = this.name,
			type = ItemType.FOLDER,
			description = null,
			isStarred = false,
			version = null,
			link = null,
			createdTime = null,
			modifiedTime = null,
			sharingUsers = null,
			size = null
		)
		is FileMetadata -> StoreMetadataItem(
			id = this.id,
			name = this.name,
			type = ItemType.FILE,
			description = null,
			isStarred = false,
			version = null,
			link = null,
			createdTime = null,
			modifiedTime = Instant.fromEpochMilliseconds(this.clientModified.time)
				.toLocalDateTime(TimeZone.currentSystemDefault()),
			sharingUsers = null,
			size = sizeConverter.run { this@toStoreMetadata.size.toBytes() }
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
		createdTime = Instant.fromEpochMilliseconds(this.createdTime.value)
			.toLocalDateTime(TimeZone.UTC),
		modifiedTime = Instant.fromEpochMilliseconds(this.modifiedTime.value)
			.toLocalDateTime(TimeZone.UTC),
		sharingUsers = this.permissions.map {
			User(
				it.emailAddress,
				it.role,
				it.displayName,
				it.photoLink
			)
		},
		size = sizeConverter.run { this@toStoreMetadata.size.toLong().toBytes() }
	)
}
