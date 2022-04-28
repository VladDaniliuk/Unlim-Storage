package com.shov.unlimstorage.di

import com.shov.unlimstorage.models.NewVersionViewModelFactory
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {
	fun newVersionViewModelFactory(): NewVersionViewModelFactory
}
