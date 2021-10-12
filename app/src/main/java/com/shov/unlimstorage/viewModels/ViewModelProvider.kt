package com.shov.unlimstorage.viewModels

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shov.unlimstorage.di.ViewModelFactoryProvider
import com.shov.unlimstorage.models.items.StoreItem
import com.shov.unlimstorage.models.items.StoreMetadataItem
import com.shov.unlimstorage.viewModels.files.FileDescriptionViewModel
import com.shov.unlimstorage.viewModels.files.FileInfoViewModel
import dagger.hilt.android.EntryPointAccessors

@Composable
fun fileDescriptionViewModel(storeMetadataItem: StoreMetadataItem): FileDescriptionViewModel {
	val factory = EntryPointAccessors.fromActivity(
		LocalContext.current as Activity,
		ViewModelFactoryProvider::class.java
	).fileDescriptionViewModelFactory()

	return viewModel(factory = FileDescriptionViewModel.provideFactory(factory, storeMetadataItem))
}

@Composable
fun fileInfoViewModel(storeItem: StoreItem): FileInfoViewModel {
	val factory = EntryPointAccessors.fromActivity(
		LocalContext.current as Activity,
		ViewModelFactoryProvider::class.java
	).fileInfoViewModelFactory()

	return viewModel(factory = FileInfoViewModel.provideFactory(factory, storeItem))
}
