package com.shov.unlimstorage.models

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ScaffoldState
import com.shov.unlimstorage.api.models.LastReleaseItem
import com.shov.unlimstorage.models.items.StoreItem
import com.shov.unlimstorage.models.items.StoreMetadataItem
import com.shov.unlimstorage.models.preferences.Preference
import com.shov.unlimstorage.viewModels.MainNavigationViewModel
import com.shov.unlimstorage.viewModels.NewVersionViewModel
import com.shov.unlimstorage.viewModels.UpdateViewModel
import com.shov.unlimstorage.viewModels.files.FileDescriptionViewModel
import com.shov.unlimstorage.viewModels.files.FileInfoViewModel
import dagger.assisted.AssistedFactory

@AssistedFactory
interface FileDescriptionViewModelFactory {
	fun createFileDescriptionViewModel(storeMetadataItem: StoreMetadataItem):
			FileDescriptionViewModel
}

@AssistedFactory
interface FileInfoViewModelFactory {
	fun createFileInfoViewModel(storeItem: StoreItem): FileInfoViewModel
}

@AssistedFactory
interface MainNavigationViewModelFactory {
	@ExperimentalMaterialApi
	fun createMainNavigationViewModel(
		scaffoldState: ScaffoldState,
		sheetState: ModalBottomSheetState
	): MainNavigationViewModel
}

@AssistedFactory
interface NewVersionViewModelFactory {
	fun createNewVersionViewModel(lastReleaseItem: LastReleaseItem): NewVersionViewModel
}

@AssistedFactory
interface UpdateViewModelFactory {
	fun createUpdateViewModel(isShowAgain: Preference<Boolean>): UpdateViewModel
}
