package com.shov.unlimstorage.di

import com.shov.unlimstorage.utils.converters.SizeConverter
import com.shov.unlimstorage.utils.converters.SizeConverterImpl
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
	fun provideSizeConverter(sizeConverterImpl: SizeConverterImpl): SizeConverter

	@Binds
	fun provideStoreMetadataConverter(
		storeMetadataConverterImpl: StoreMetadataConverterImpl
	): StoreMetadataConverter
}
