package com.shov.unlimstorage.models.repositories.files

import com.shov.unlimstorage.db.StoreItemDao
import com.shov.coremodels.ItemType
import com.shov.coremodels.StoreItem
import com.shov.coremodels.StoreMetadataItem
import com.shov.coremodels.StorageType
import com.shov.unlimstorage.utils.reduce
import javax.inject.Inject

interface FilesInfoRepository {
	fun checkLocal(parentFolder: String? = null, disk: StorageType? = null): List<StoreItem>
	fun checkRemote(parentFolder: String? = null, disk: StorageType? = null): List<StoreItem>
	fun downloadFile(
		disk: StorageType,
		id: String,
		name: String,
		size: Long,
		setPercents: (Float, String) -> Unit
	)

	fun getFromLocal(parentFolder: String? = null): List<StoreItem>
	fun getFromRemote(parentFolder: String? = null, disk: StorageType? = null): List<StoreItem>
	fun getLocalItem(id: String): StoreItem
	fun getRemoteMetadata(id: String, disk: StorageType, type: ItemType): StoreMetadataItem?
	fun setToLocal(storeItemList: List<StoreItem>)
}

class FilesInfoRepositoryImpl @Inject constructor(
	private val filesFactory: FilesFactory,
	private val storeItemDao: StoreItemDao
) : FilesInfoRepository {
	override fun checkLocal(parentFolder: String?, disk: StorageType?): List<StoreItem> {
		var storeItemList = getFromLocal(parentFolder)

		if (storeItemList.isEmpty()) {
			storeItemList = getFromRemote(parentFolder, disk)
		}

		setToLocal(storeItemList)
		return storeItemList
	}

	override fun checkRemote(parentFolder: String?, disk: StorageType?): List<StoreItem> {
		val storeItemList = getFromRemote(parentFolder, disk)

		disk?.let {
			storeItemDao.deleteFiles(parentFolder, disk)
		} ?: storeItemDao.deleteFiles(parentFolder)

		setToLocal(storeItemList)
		return storeItemList
	}

	override fun downloadFile(
		disk: StorageType,
		id: String,
		name: String,
		size: Long,
		setPercents: (Float, String) -> Unit
	) {
		filesFactory.create(disk).downloadFile(id, name, size, setPercents)
	}

	override fun getFromLocal(parentFolder: String?): List<StoreItem> {
		return storeItemDao.getFiles(parentFolder)
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

	override fun getLocalItem(id: String) = storeItemDao.getFile(id)

	override fun getRemoteMetadata(id: String, disk: StorageType, type: ItemType) =
		filesFactory.create(disk).getFileMetadata(id, type)

	override fun setToLocal(storeItemList: List<StoreItem>) {
		storeItemDao.setAll(storeItemList)
	}
}
