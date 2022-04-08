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
		onDownload: (String) -> Unit,
		onStart: () -> Unit,
		onError: () -> Unit
	)

	fun uploadFile(
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
		onDownload: (String) -> Unit,//TODO:Delete, now it's only for name
		onStart: () -> Unit,
		onError: () -> Unit
	) {
		onStart()
		downloadElement(disk, name, id, ItemType.FOLDER, onDownload, onError)
		//TODO GOOGLE PERCENTS
		// TODO onExist onError
	}

	private fun downloadElement(
		disk: StorageType,
		name: String,
		id: String,
		itemType: ItemType,
		onDownload: (String) -> Unit,
		onError: () -> Unit,
		parentFolder: File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
	) {
		if (itemType == ItemType.FOLDER) {
			downloadFolder(id, name, disk, parentFolder).forEach {
				downloadElement(
					disk,
					it.name,
					it.id,
					it.type,
					onDownload,
					onError,
					File(parentFolder, name)
				)
			}
		} else {
			downloadFile(id, name, disk, parentFolder, onDownload, onError)
		}
	}

	private fun downloadFolder(id: String, name: String, disk: StorageType, parentFolder: File)
			: List<StoreItem> {
		return if (File(parentFolder, name).exists().not()) {
			if (File(parentFolder, name).mkdirs()) {
				filesFactory.create(disk).getFiles(id)
			} else emptyList()
		} else emptyList()
	}

	private fun downloadFile(
		id: String,
		name: String,
		disk: StorageType,
		parentFolder: File,
		onDownload: (String) -> Unit,
		onError: () -> Unit
	) {
		File(parentFolder, name).createFile(onCreate = {
			filesFactory.create(disk).downloadFile(id, name, this, onDownload, onError)
		})
	}

	override fun uploadFile(
		inputStream: InputStream,
		name: String,
		storageType: StorageType,
		folderId: String?
	) {
		filesFactory.create(storageType).uploadFile(inputStream, name, folderId)
	}
}
