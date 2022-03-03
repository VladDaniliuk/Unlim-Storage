package com.shov.unlimstorage.models.repositories.files

import com.room.shov.StoreItemDataSource
import com.shov.coremodels.models.ItemType
import com.shov.coremodels.models.StorageType
import com.shov.coremodels.models.StoreItem
import com.shov.coremodels.models.StoreMetadataItem
import com.shov.unlimstorage.utils.files.createFile
import com.shov.unlimstorage.utils.reduce
import java.io.File
import javax.inject.Inject

interface FilesInfoRepository {
	suspend fun checkLocal(parentFolder: String? = null, disk: StorageType? = null): List<StoreItem>
	suspend fun checkRemote(parentFolder: String? = null, disk: StorageType? = null): List<StoreItem>
	fun downloadFile(
		disk: StorageType,
		id: String,
		name: String,
		size: Long,
		setPercents: (Float, String) -> Unit,
		onStart: () -> Unit,
		onError: () -> Unit
	)

	suspend fun getFromLocal(parentFolder: String? = null): List<StoreItem>
	fun getFromRemote(parentFolder: String? = null, disk: StorageType? = null): List<StoreItem>
	suspend fun getLocalItem(id: String): StoreItem
	fun getRemoteMetadata(id: String, disk: StorageType, type: ItemType): StoreMetadataItem?
	suspend fun setToLocal(storeItemList: List<StoreItem>)
}

class FilesInfoRepositoryImpl @Inject constructor(
	private val filesFactory: FilesFactory,
	private val storeItemDataSource: StoreItemDataSource
) : FilesInfoRepository {
	override suspend fun checkLocal(parentFolder: String?, disk: StorageType?): List<StoreItem> {
		var storeItemList = getFromLocal(parentFolder)

		if (storeItemList.isEmpty()) {
			storeItemList = getFromRemote(parentFolder, disk)
		}

		setToLocal(storeItemList)
		return storeItemList
	}

	override suspend fun checkRemote(parentFolder: String?, disk: StorageType?): List<StoreItem> {
		val storeItemList = getFromRemote(parentFolder, disk)

		disk?.let {
			storeItemDataSource.deleteFiles(parentFolder, disk)
		} ?: storeItemDataSource.deleteFiles(parentFolder)

		setToLocal(storeItemList)
		return storeItemList
	}

	override fun downloadFile(
		disk: StorageType,
		id: String,
		name: String,
		size: Long,
		setPercents: (Float, String) -> Unit,
		onStart: () -> Unit,
		onError: () -> Unit
	) {
		File("/storage/emulated/0/Download").createFile(
			name = name,
			onCreate = {
				filesFactory.create(disk)
					.downloadFile(id, name, size, this, setPercents, onStart, onError)
			}//TODO onExist onError
		)//TODO GOOGLE PERCENTS
	}

	override suspend fun getFromLocal(parentFolder: String?): List<StoreItem> {
		return storeItemDataSource.getFiles(parentFolder)
			.sortedBy { storeItem ->
				storeItem.name.uppercase()
			}
	}

	override fun getFromRemote(parentFolder: String?, disk: StorageType?): List<StoreItem> {
		return disk?.let {
			filesFactory.create(disk).getFiles(folderId = parentFolder)
		} ?: run {
			reduce { storageType ->
				filesFactory.create(storageType).getFiles(parentFolder)
			}.sortedBy { storeItem ->
				storeItem.name.uppercase()
			}
		}
	}

	override suspend fun getLocalItem(id: String) = storeItemDataSource.getFile(id)

	override fun getRemoteMetadata(id: String, disk: StorageType, type: ItemType) =
		filesFactory.create(disk).getFileMetadata(id, type)

	override suspend fun setToLocal(storeItemList: List<StoreItem>) {
		storeItemDataSource.setAll(storeItemList)
	}
}
