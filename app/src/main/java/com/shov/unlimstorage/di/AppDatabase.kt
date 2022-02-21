package com.shov.unlimstorage.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shov.coremodels.models.StoreItem
import com.shov.unlimstorage.db.StoreItemDao

@Database(entities = [StoreItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
	abstract fun storeItemDao(): StoreItemDao
}
