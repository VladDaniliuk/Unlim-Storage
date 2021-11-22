package com.shov.unlimstorage.di

import com.shov.unlimstorage.models.MainNavigationViewModelFactory
import com.shov.unlimstorage.models.NewVersionViewModelFactory
import com.shov.unlimstorage.models.UpdateViewModelFactory
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {
	fun mainNavigationViewModelFactory(): MainNavigationViewModelFactory
	fun newVersionViewModelFactory(): NewVersionViewModelFactory
	fun updateViewModelFactory(): UpdateViewModelFactory
}
