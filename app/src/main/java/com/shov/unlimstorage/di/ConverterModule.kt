package com.shov.unlimstorage.di

import com.shov.unlimstorage.utils.converters.*
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
	fun provideStoreConverter(storeConverterImpl: StoreConverterImpl): StoreConverter

	@Binds
	fun provideStoreMetadataConverter(
		storeMetadataConverterImpl: StoreMetadataConverterImpl
	): StoreMetadataConverter
}
