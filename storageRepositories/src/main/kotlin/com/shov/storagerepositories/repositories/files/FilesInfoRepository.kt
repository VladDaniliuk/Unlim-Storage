package com.shov.storagerepositories.repositories.files

import com.shov.coremodels.models.ItemType
import com.shov.coremodels.models.StorageType
import com.shov.coremodels.models.StoreItem
import com.shov.coremodels.models.StoreMetadataItem
import com.shov.localstorage.StoreItemDataSource
import com.shov.storagerepositories.repositories.factories.FilesFactory
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FilesInfoRepository {
	fun getFromLocalAsync(folderId: String?): Flow<List<StoreItem>>
	fun getLocalItemAsync(id: String): Flow<StoreItem>
	fun getRemoteMetadata(id: String, disk: StorageType, type: ItemType): StoreMetadataItem?
	suspend fun getFromRemote(storageType: StorageType?, folderId: String?)
}

class FilesInfoRepositoryImpl @Inject constructor(
	private val filesFactory: FilesFactory,
	private val storeItemDao: StoreItemDataSource
) : FilesInfoRepository {
	override suspend fun getFromRemote(storageType: StorageType?, folderId: String?) {
		val storeItems = mutableListOf<Deferred<List<StoreItem>>>()
		storageType?.let {
			coroutineScope {
				storeItems.add(
					async {
						filesFactory.create(storageType).getFiles(folderId)
					}
				)
			}
		} ?: run {
			coroutineScope {
				StorageType.values().forEach { storageType ->
					storeItems.add(
						async {
							filesFactory.create(storageType).getFiles(folderId)
						}
					)
				}
			}
		}

		val storeItemList = storeItems.awaitAll().flatten()

		storeItemDao.getFiles(folderId).filterNot(storeItemList::contains)
			.map(StoreItem::id)
			.let { id ->
				storeItemDao.deleteFiles(id)
			}

		storeItemDao.setAll(storeItemList)
	}

	override fun getFromLocalAsync(folderId: String?): Flow<List<StoreItem>> =
		storeItemDao.getFilesAsync(folderId)

	override fun getLocalItemAsync(id: String): Flow<StoreItem> = storeItemDao.getFileAsync(id)

	override fun getRemoteMetadata(id: String, disk: StorageType, type: ItemType) =
		filesFactory.create(disk).getFileMetadata(id, type)
}
