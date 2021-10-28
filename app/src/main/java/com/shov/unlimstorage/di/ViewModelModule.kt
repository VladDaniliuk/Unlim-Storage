package com.shov.unlimstorage.di

import com.shov.unlimstorage.models.*
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {
	fun fileDescriptionViewModelFactory(): FileDescriptionViewModelFactory
	fun fileInfoViewModelFactory(): FileInfoViewModelFactory
	fun newVersionViewModelFactory(): NewVersionViewModelFactory
	fun mainNavigationViewModelFactory(): MainNavigationViewModelFactory
}
