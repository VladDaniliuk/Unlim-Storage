package com.shov.dropboxstorage.converters

import android.icu.text.SimpleDateFormat
import com.dropbox.core.v2.files.FileMetadata
import com.dropbox.core.v2.files.FolderMetadata
import com.dropbox.core.v2.files.Metadata
import com.shov.coremodels.converters.toBytes
import com.shov.coremodels.models.ItemType
import com.shov.coremodels.models.StorageType
import com.shov.coremodels.models.StoreItem
import com.shov.coremodels.models.StoreMetadataItem
import java.util.*

internal fun Metadata.toStoreItem(
    parentFolder: String?,
    toSize: Pair<String, Int>.() -> String
): StoreItem = when (this) {
    is FolderMetadata -> StoreItem(
        id = id,
        type = ItemType.FOLDER,
        name = name,
        parentFolder = parentFolder,
        disk = StorageType.DROPBOX
    )
    is FileMetadata -> StoreItem(
        id = id,
        type = ItemType.FILE,
        name = name,
        size = size.toBytes().let(toSize),
        parentFolder = parentFolder,
        disk = StorageType.DROPBOX
    )
    else -> throw Exception("Converter works only with File and Folder metadata")
}

internal fun Metadata.toStoreMetadataItem(): StoreMetadataItem = when (this) {
    is FolderMetadata -> StoreMetadataItem(
        id = id,
        name = name,
        type = ItemType.FOLDER
    )
    is FileMetadata -> StoreMetadataItem(
        id = id,
        name = name,
        type = ItemType.FILE,
        createdTime = fileLockInfo?.created?.toPrettyTime(),
        modifiedTime = clientModified?.toPrettyTime(),
        size = size
    )
    else -> throw Exception("Converter works only with File and Folder metadata")
}

internal fun Date.toPrettyTime(): String =
    SimpleDateFormat("yyyy MMMM d HH:mm", Locale.getDefault()).format(this)