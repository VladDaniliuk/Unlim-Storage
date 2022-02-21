package com.shov.storage

import com.shov.coremodels.models.ItemType
import com.shov.coremodels.models.StoreItem
import com.shov.coremodels.models.StoreMetadataItem
import java.io.InputStream
import java.io.File

interface FilesDataSource {
	fun createFolder(folderId: String?, folderName: String): Boolean
	fun downloadFile(
		id: String,
		name: String,
		size: Long,
		file: File,
		setPercents: (Float, String) -> Unit
	)

	fun getFileMetadata(id: String, type: ItemType): StoreMetadataItem?
	fun getFiles(folderId: String? = null): List<StoreItem>
	fun uploadFile(inputStream: InputStream, name: String, folderId: String?)
}
