package com.shov.unlimstorage.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shov.unlimstorage.api.models.LastReleaseItem
import com.shov.unlimstorage.models.NewVersionViewModelFactory
import com.shov.unlimstorage.utils.converters.SizeConverter
import com.shov.unlimstorage.utils.converters.toPrettyString
import com.shov.unlimstorage.values.UNCHECKED_CAST
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class NewVersionViewModel @AssistedInject constructor(
	@Assisted private val lastReleaseItem: LastReleaseItem,
	private val sizeConverter: SizeConverter
) : ViewModel() {
	private val _lastRelease by mutableStateOf(lastReleaseItem)
	val lastRelease get() = _lastRelease

	fun getSize() = sizeConverter.run { _lastRelease.applicationSize.toBytes() }

	fun getReleaseDate() = _lastRelease.releaseDate.toPrettyString()

	@Suppress(UNCHECKED_CAST)
	companion object {
		fun provideFactory(
			assistedFactory: NewVersionViewModelFactory,
			lastReleaseItem: LastReleaseItem
		): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
			override fun <T : ViewModel?> create(modelClass: Class<T>): T {
				return assistedFactory.createNewVersionViewModel(lastReleaseItem) as T
			}
		}
	}
}
