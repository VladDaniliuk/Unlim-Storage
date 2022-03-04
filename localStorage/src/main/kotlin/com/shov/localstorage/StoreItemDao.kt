package com.shov.localstorage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shov.coremodels.models.StorageType
import com.shov.coremodels.models.StoreItem

@Dao
interface StoreItemDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun setAll(storeItems: List<StoreItem>)

	@Query("SELECT * FROM StoreItem WHERE parentFolder is :parentFolder")
	fun getFiles(parentFolder: String? = null): List<StoreItem>

	@Query("SELECT * FROM StoreItem WHERE id is :id")
	fun getFile(id: String): StoreItem

	@Query("DELETE FROM StoreItem WHERE parentFolder is :parentFolder and disk is :disk")
	fun deleteFiles(parentFolder: String? = null, disk: StorageType)

	@Query("DELETE FROM StoreItem WHERE parentFolder is :parentFolder")
	fun deleteFiles(parentFolder: String? = null)
}
