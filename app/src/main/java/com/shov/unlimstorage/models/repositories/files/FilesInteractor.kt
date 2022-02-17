package com.shov.unlimstorage.models.repositories.files

import com.shov.coremodels.ItemType
import com.shov.coremodels.StoreItem
import com.shov.coremodels.StoreMetadataItem
import java.io.InputStream

interface FilesInteractor {
	fun createFolder(folderId: String?, folderName: String): Boolean
	fun downloadFile(id: String, name: String, size: Long, setPercents: (Float, String) -> Unit)
	fun getFileMetadata(id: String, type: ItemType): StoreMetadataItem?
	fun getFiles(folderId: String? = null): List<StoreItem>
	fun uploadFile(inputStream: InputStream, name: String, folderId: String?)
}
