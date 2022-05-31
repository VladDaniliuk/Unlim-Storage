package com.shov.localstorage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shov.coremodels.models.DownloadedItem
import com.shov.coremodels.models.StoreItem

@Database(entities = [StoreItem::class, DownloadedItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun storeItemDataSource(): StoreItemDataSource
}
