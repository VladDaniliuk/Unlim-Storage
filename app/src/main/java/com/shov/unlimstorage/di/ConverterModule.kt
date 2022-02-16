package com.shov.unlimstorage.di

import com.shov.unlimstorage.utils.converters.StoreItemConverter
import com.shov.unlimstorage.utils.converters.StoreItemConverterImpl
import com.shov.unlimstorage.utils.converters.StoreMetadataConverter
import com.shov.unlimstorage.utils.converters.StoreMetadataConverterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ConverterModule {
	@Binds
	fun provideStoreMetadataConverter(
		storeMetadataConverterImpl: StoreMetadataConverterImpl
	): StoreMetadataConverter

	@Binds
	fun provideStoreItemConverter(storeItemConverterImpl: StoreItemConverterImpl):
			StoreItemConverter
}
