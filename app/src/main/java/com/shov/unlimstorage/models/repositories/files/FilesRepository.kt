package com.shov.unlimstorage.models.repositories.files

import com.shov.unlimstorage.db.StoreItemDao
import com.shov.unlimstorage.models.items.ItemType
import com.shov.unlimstorage.models.items.StoreItem
import com.shov.unlimstorage.models.items.StoreMetadataItem
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import javax.inject.Inject

interface FilesRepository {
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
	suspend fun createFolder(
		folderId: String?,
		folderName: String,
		storageType: StorageType
	): Boolean
}

class FilesRepositoryImpl @Inject constructor(
	private val filesFactory: FilesFactory,
	private val storeItemDao: StoreItemDao
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

	override fun getLocalItem(id: String) = storeItemDao.getFile(id)

	override fun getRemoteMetadata(id: String, disk: StorageType, type: ItemType) =
		filesFactory.create(disk).getFileMetadata(id, type)

	override fun setToLocal(storeItemList: List<StoreItem>) {
		storeItemDao.setAll(storeItems = storeItemList)
	}

	override suspend fun createFolder(
		folderId: String?,
		folderName: String,
		storageType: StorageType
	) = filesFactory.create(storageType).createFolder(folderId, folderName)
}
