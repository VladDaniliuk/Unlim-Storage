package com.shov.localstorage

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
	@Provides
	@Singleton
	fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
		return Room.databaseBuilder(context, AppDatabase::class.java, "store_item_database").build()
	}

	@Provides
	@Singleton
	fun provideStoreItemDao(appDatabase: AppDatabase): StoreItemDataSource =
		appDatabase.storeItemDataSource()
}
