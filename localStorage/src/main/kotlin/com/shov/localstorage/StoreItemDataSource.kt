package com.shov.localstorage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shov.coremodels.models.StorageType
import com.shov.coremodels.models.StoreItem
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreItemDataSource {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun setAll(storeItems: List<StoreItem>)

	@Query("SELECT * FROM StoreItem WHERE parentFolder is :parentFolder")
	fun getFilesAsync(parentFolder: String? = null): Flow<List<StoreItem>>

	@Query("SELECT * FROM StoreItem WHERE id is :id")
	fun getFileAsync(id: String): Flow<StoreItem>

	@Query("DELETE FROM StoreItem WHERE parentFolder is :folderId and disk is :disk")
	suspend fun deleteFiles(folderId: String? = null, disk: StorageType)

	@Query("DELETE FROM StoreItem WHERE parentFolder is :parentFolder")
	suspend fun deleteFiles(parentFolder: String? = null)
}
