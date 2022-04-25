package com.shov.storagerepositories.repositories.files

import android.os.Environment
import com.shov.coremodels.models.ItemType
import com.shov.coremodels.models.StorageType
import com.shov.coremodels.models.StoreItem
import com.shov.storagerepositories.repositories.factories.FilesFactory
import com.shov.storagerepositories.utils.createFile
import java.io.File
import java.io.InputStream
import javax.inject.Inject

interface FileActionsRepository {
	suspend fun createFolder(
		folderId: String?,
		folderName: String,
		storageType: StorageType
	): Boolean

	fun download(
		disk: StorageType,
		id: String,
		name: String,
		onError: () -> Unit,
		parentFolder: File = Environment
			.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
		type: ItemType,
	)

	suspend fun uploadFile(
		inputStream: InputStream,
		name: String,
		storageType: StorageType,
		folderId: String?
	)
}

class FileActionsRepositoryImpl @Inject constructor(
	private val filesFactory: FilesFactory
) : FileActionsRepository {
	override suspend fun createFolder(
		folderId: String?,
		folderName: String,
		storageType: StorageType
	) = filesFactory.create(storageType).createFolder(folderId, folderName)

	override fun download(
		disk: StorageType,
		id: String,
		name: String,
		onError: () -> Unit,
		parentFolder: File,
		type: ItemType,
	) {
		if (type == ItemType.FOLDER) {
			downloadFolder(id, name, disk, parentFolder).forEach { storeItem ->
				download(
					storeItem.disk,
					storeItem.id,
					storeItem.name,
					onError,
					File(parentFolder, name),
					storeItem.type
				)
			}
		} else {
			downloadFile(disk, id, name, onError, parentFolder)
		}
		//TODO GOOGLE PERCENTS
		// TODO onExist onError
	}

	private fun downloadFolder(
		id: String,
		name: String,
		disk: StorageType,
		parentFolder: File
	): List<StoreItem> {
		return if (File(parentFolder, name).exists().not()) {
			if (File(parentFolder, name).mkdirs()) {
				filesFactory.create(disk).getFiles(id)
			} else emptyList()
		} else emptyList()
	}

	private fun downloadFile(
		disk: StorageType,
		id: String,
		name: String,
		onError: () -> Unit,
		parentFolder: File,
	) {
		File(parentFolder, name).createFile(onCreate = {
			filesFactory.create(disk).downloadFile(id, name, this, onError)
		})
	}

	override suspend fun uploadFile(
		inputStream: InputStream,
		name: String,
		storageType: StorageType,
		folderId: String?
	) {
		filesFactory.create(storageType).uploadFile(inputStream, name, folderId)
	}
}
