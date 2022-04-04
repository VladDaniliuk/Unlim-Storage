package com.shov.storagerepositories.repositories.files

import android.os.Environment
import com.shov.coremodels.models.StorageType
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

	fun downloadFile(
		disk: StorageType,
		id: String,
		name: String,
		size: Long,
		setPercents: (Float, String) -> Unit,
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

	override fun downloadFile(
		disk: StorageType,
		id: String,
		name: String,
		size: Long,
		setPercents: (Float, String) -> Unit,
		onStart: () -> Unit,
		onError: () -> Unit
	) {
		File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), name)
			.createFile(
				onCreate = {
					filesFactory.create(disk)
						.downloadFile(id, name, size, this, setPercents, onStart, onError)
				}
			)//TODO GOOGLE PERCENTS
	}// TODO onExist onError

	override fun uploadFile(
		inputStream: InputStream,
		name: String,
		storageType: StorageType,
		folderId: String?
	) {
		filesFactory.create(storageType).uploadFile(inputStream, name, folderId)
	}
}
