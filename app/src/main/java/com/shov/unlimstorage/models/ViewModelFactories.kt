package com.shov.unlimstorage.models

import com.shov.unlimstorage.models.items.StoreItem
import com.shov.unlimstorage.models.items.StoreMetadataItem
import com.shov.unlimstorage.viewModels.files.FileActionsViewModel
import com.shov.unlimstorage.viewModels.files.FileDescriptionViewModel
import com.shov.unlimstorage.viewModels.files.FileInfoViewModel
import dagger.assisted.AssistedFactory

@AssistedFactory
interface FileInfoViewModelFactory {
	fun createFileInfoViewModel(storeItem: StoreItem): FileInfoViewModel
}

@AssistedFactory
interface FileDescriptionViewModelFactory {
	fun createFileDescriptionViewModel(storeMetadataItem: StoreMetadataItem):
			FileDescriptionViewModel
}

@AssistedFactory
interface FileActionViewModelFactory {
	fun createFileActionsViewModelFactory(storeItem: StoreItem): FileActionsViewModel
}
