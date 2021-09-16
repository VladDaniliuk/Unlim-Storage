package com.shov.unlimstorage.models.filesRepository

import com.shov.unlimstorage.db.StoreItemDao
import com.shov.unlimstorage.models.StoreItem
import com.shov.unlimstorage.models.signInModels.StorageType
import javax.inject.Inject

interface FilesRepository {
	fun checkLocal(parentFolder: String? = null, disk: StorageType? = null): List<StoreItem>
	fun checkRemote(parentFolder: String? = null, disk: StorageType? = null): List<StoreItem>
	fun getFromLocal(parentFolder: String? = null): List<StoreItem>
	fun getFromRemote(parentFolder: String? = null, disk: StorageType? = null): List<StoreItem>
	fun setToLocal(storeItemList: List<StoreItem>)
}

class FilesRepositoryImpl @Inject constructor(
	private val storeItemDao: StoreItemDao,
	private val filesFactory: FilesFactory
) :
	FilesRepository {
	override fun checkLocal(parentFolder: String?, disk: StorageType?): List<StoreItem> {
		var storeItemList = getFromLocal(parentFolder = parentFolder)

		if (storeItemList.isEmpty()) {
			storeItemList = getFromRemote(
				parentFolder = parentFolder,
				disk = disk
			)
		}

		setToLocal(storeItemList)
		return storeItemList
	}

	override fun checkRemote(parentFolder: String?, disk: StorageType?): List<StoreItem> {
		val storeItemList = getFromRemote(
			parentFolder = parentFolder,
			disk = disk
		)

		disk?.let {
			storeItemDao.deleteFiles(
				parentFolder = parentFolder,
				disk = disk
			)
		} ?: storeItemDao.deleteFiles(parentFolder = parentFolder)

		setToLocal(storeItemList = storeItemList)
		return storeItemList
	}

	override fun getFromLocal(parentFolder: String?): List<StoreItem> {
		return storeItemDao.getFiles(parentFolder = parentFolder)
			.sortedBy { storeItem ->
				storeItem.name.uppercase()
			}
	}

	override fun getFromRemote(parentFolder: String?, disk: StorageType?): List<StoreItem> {
		return disk?.let {
			filesFactory.create(disk).getFiles(folderId = parentFolder)
		} ?: run {
			filesFactory
				.create(StorageType.DROPBOX)
				.getFiles(folderId = parentFolder)
				.plus(
					filesFactory
						.create(StorageType.BOX)
						.getFiles(folderId = parentFolder)
						.asIterable()
				).plus(
					filesFactory
						.create(StorageType.GOOGLE)
						.getFiles(folderId = parentFolder)
						.asIterable()
				)
		}.sortedBy { storeItem ->
			storeItem.name.uppercase()
		}
	}

	override fun setToLocal(storeItemList: List<StoreItem>) {
		storeItemDao.setAll(storeItems = storeItemList)
	}
}
