package com.shov.preferences.di

import com.shov.preferences.datasources.PreferencesDataSource
import com.shov.preferences.datasources.PreferencesDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface PreferencesModule {
	@Binds
	fun bindsPreferencesDataSource(preferencesDataSourceImpl: PreferencesDataSourceImpl):
			PreferencesDataSource
}