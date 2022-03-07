package com.shov.boxstorage.converters

import com.box.androidsdk.content.BoxApi
import com.box.androidsdk.content.BoxApiFile
import com.box.androidsdk.content.BoxApiFolder
import com.box.androidsdk.content.models.BoxItem
import com.box.androidsdk.content.requests.BoxRequestsFile
import com.box.androidsdk.content.requests.BoxRequestsFolder
import com.shov.coremodels.converters.toBytes
import com.shov.coremodels.models.*
import java.text.SimpleDateFormat
import java.util.*

fun <T : BoxApi> T.getFileMetadata(id: String, type: ItemType): StoreMetadataItem {
	when (this) {
		is BoxApiFile -> getInfoRequest(id)
		is BoxApiFolder -> getInfoRequest(id)
		else -> throw Error()
	}.setFields(
		"name",
		"description",
		"collections",
		"shared_link",
		"created_at",
		"created_by",
		"modified_at",
		"size"
	).run {
		when (this) {
			is BoxRequestsFolder.GetFolderInfo -> send()
			is BoxRequestsFile.GetFileInfo -> send()
			else -> throw Error()
		}.run {
			return StoreMetadataItem(
				id = id,
				name = name,
				type = type,
				description = description,
				isStarred = collections?.run { size > 0 } ?: false,
				link = sharedLink?.url,
				createdTime = createdAt.toPrettyTime(),
				modifiedTime = modifiedAt.toPrettyTime(),
				sharingUsers = listOf(
					UserItem(
						createdBy.login,
						"Modifier",
						createdBy.name
					)
				),
				size = if (type == ItemType.FILE) size else null
			)
		}
	}
}

inline fun BoxItem.toStoreItem(folderId: String?, toSize: Pair<String, Int>.() -> String) =
	StoreItem(
		id = id,
		type = ItemType.valueOf(type.uppercase()),
		name = name,
		size = if (ItemType.valueOf(type.uppercase()) == ItemType.FILE)
			size.toBytes().toSize()
		else null,
		parentFolder = folderId,//Need only folderId for working with room
		disk = StorageType.BOX
	)

fun Date.toPrettyTime(): String =
	SimpleDateFormat("yyyy MMMM d HH:mm", Locale.getDefault()).format(this)
