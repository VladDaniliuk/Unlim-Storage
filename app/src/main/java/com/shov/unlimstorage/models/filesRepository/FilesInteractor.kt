package com.shov.unlimstorage.models.filesRepository

import com.shov.unlimstorage.models.items.ItemType
import com.shov.unlimstorage.models.items.StoreItem
import com.shov.unlimstorage.models.items.StoreMetadataItem

interface FilesInteractor {
	fun getFiles(folderId: String? = null): List<StoreItem>
	fun getFileMetadata(id: String, type: ItemType): StoreMetadataItem?
}
