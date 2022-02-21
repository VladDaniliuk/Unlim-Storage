package com.shov.unlimstorage.models.repositories.files

import com.shov.coremodels.models.StorageType
import java.io.InputStream
import javax.inject.Inject

interface NewFileRepository {
	fun uploadFile(
		inputStream: InputStream,
		name: String,
		storageType: StorageType,
		folderId: String?
	)

	suspend fun createFolder(
		folderId: String?,
		folderName: String,
		storageType: StorageType
	): Boolean
}

class NewFileRepositoryImpl @Inject constructor(
	private val filesFactory: FilesFactory
) : NewFileRepository {
	override fun uploadFile(
		inputStream: InputStream,
		name: String,
		storageType: StorageType,
		folderId: String?
	) {
		filesFactory.create(storageType).uploadFile(inputStream, name, folderId)
	}

	override suspend fun createFolder(
		folderId: String?,
		folderName: String,
		storageType: StorageType
	) = filesFactory.create(storageType).createFolder(folderId, folderName)
}
