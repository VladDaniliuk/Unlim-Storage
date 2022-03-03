package com.shov.unlimstorage.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.room.shov.StoreItemDataSource
import com.shov.coremodels.models.StoreItem

@Database(entities = [StoreItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
	abstract fun storeItemDao(): StoreItemDataSource
}
