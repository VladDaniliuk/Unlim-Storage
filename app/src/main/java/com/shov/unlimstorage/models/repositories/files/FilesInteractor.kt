package com.shov.unlimstorage.models.repositories.files

import com.shov.unlimstorage.models.items.ItemType
import com.shov.unlimstorage.models.items.StoreItem
import com.shov.unlimstorage.models.items.StoreMetadataItem

interface FilesInteractor {
	fun getFiles(folderId: String? = null): List<StoreItem>
	fun getFileMetadata(id: String, type: ItemType): StoreMetadataItem?
	fun downloadFile(id: String, name: String, size: Long, setPercents: (Float,String) -> Unit)
}
