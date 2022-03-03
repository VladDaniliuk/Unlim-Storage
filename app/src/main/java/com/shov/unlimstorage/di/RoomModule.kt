package com.shov.unlimstorage.di

import android.content.Context
import androidx.room.Room
import com.room.shov.StoreItemDataSource
import com.shov.unlimstorage.values.DATABASE_NAME
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
		return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
	}

	@Provides
	@Singleton
	fun provideStoreItemDao(appDatabase: AppDatabase): StoreItemDataSource {
		return appDatabase.storeItemDao()
	}
}
