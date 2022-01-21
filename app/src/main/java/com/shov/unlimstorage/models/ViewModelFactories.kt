package com.shov.unlimstorage.models

import com.shov.unlimstorage.api.models.LastReleaseItem
import com.shov.unlimstorage.viewModels.settings.NewVersionViewModel
import dagger.assisted.AssistedFactory

@AssistedFactory
interface NewVersionViewModelFactory {
	fun createNewVersionViewModel(lastReleaseItem: LastReleaseItem): NewVersionViewModel
}
