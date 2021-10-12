package com.shov.unlimstorage.di

import com.shov.unlimstorage.models.FileActionViewModelFactory
import com.shov.unlimstorage.models.FileDescriptionViewModelFactory
import com.shov.unlimstorage.models.FileInfoViewModelFactory
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {
	fun fileInfoViewModelFactory(): FileInfoViewModelFactory
	fun fileDescriptionViewModelFactory(): FileDescriptionViewModelFactory
	fun fileActionViewModelFactory(): FileActionViewModelFactory
}
