package com.shov.unlimstorage.models.repositories.files

import com.shov.unlimstorage.models.items.ItemType
import com.shov.unlimstorage.models.items.StoreItem
import com.shov.unlimstorage.models.items.StoreMetadataItem
import java.io.InputStream

interface FilesInteractor {
	fun createFolder(folderId: String?, folderName: String): Boolean
	fun downloadFile(id: String, name: String, size: Long, setPercents: (Float, String) -> Unit)
	fun getFileMetadata(id: String, type: ItemType): StoreMetadataItem?
	fun getFiles(folderId: String? = null): List<StoreItem>
	fun uploadFile(inputStream: InputStream, name: String, folderId: String?)
}
