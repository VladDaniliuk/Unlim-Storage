package com.shov.unlimstorage.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shov.unlimstorage.db.StoreItemDao
import com.shov.unlimstorage.models.items.StoreItem

@Database(entities = [StoreItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
	abstract fun storeItemDao(): StoreItemDao
}
