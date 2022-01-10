package com.shov.unlimstorage.models

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ScaffoldState
import com.shov.unlimstorage.api.models.LastReleaseItem
import com.shov.unlimstorage.models.preferences.Preference
import com.shov.unlimstorage.viewModels.navigations.MainNavigationViewModel
import com.shov.unlimstorage.viewModels.settings.NewVersionViewModel
import com.shov.unlimstorage.viewModels.settings.UpdateViewModel
import dagger.assisted.AssistedFactory

@AssistedFactory
interface MainNavigationViewModelFactory {
	@OptIn(ExperimentalMaterialApi::class)
	fun createMainNavigationViewModel(
		scaffoldState: ScaffoldState,
		sheetState: ModalBottomSheetState,
		isLogInPref: Preference<Boolean>
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
