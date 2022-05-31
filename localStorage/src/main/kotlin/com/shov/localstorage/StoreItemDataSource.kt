package com.shov.localstorage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shov.coremodels.models.DownloadedItem
import com.shov.coremodels.models.StoreItem
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreItemDataSource {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setAll(storeItems: List<StoreItem>)

    @Query("SELECT * FROM StoreItem WHERE parentFolder is :parentFolder ORDER BY LOWER(name) ASC")
    fun getFilesAsync(parentFolder: String? = null): Flow<List<StoreItem>>

    @Query("SELECT * FROM StoreItem WHERE parentFolder is :parentFolder")
    fun getFiles(parentFolder: String? = null): List<StoreItem>

    @Query("SELECT * FROM StoreItem WHERE id is :id")
    fun getFileAsync(id: String): Flow<StoreItem>

    @Query("DELETE FROM StoreItem WHERE id in (:ids)")
    suspend fun deleteFiles(ids: List<String>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDownloadedFile(downloadedItem: DownloadedItem)

    @Query("SELECT * FROM DownloadedItem")
    fun getDownloadedFiles(): Flow<List<DownloadedItem>>
}
