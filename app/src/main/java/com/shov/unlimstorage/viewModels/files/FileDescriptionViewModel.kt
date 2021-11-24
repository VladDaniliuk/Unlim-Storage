package com.shov.unlimstorage.viewModels.files

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shov.unlimstorage.models.FileDescriptionViewModelFactory
import com.shov.unlimstorage.models.items.StoreMetadataItem
import com.shov.unlimstorage.values.UNCHECKED_CAST
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class FileDescriptionViewModel @AssistedInject constructor(
	@Assisted private val metadata: StoreMetadataItem,
) : ViewModel() {
	private var _storeMetadata by mutableStateOf(metadata)
	val storeMetadata get() = _storeMetadata

	fun setDescription(newDescription: String) {
		_storeMetadata = _storeMetadata.copy(description = newDescription)
	}

	@Suppress(UNCHECKED_CAST)
	companion object {
		fun provideFactory(
			assistedFactory: FileDescriptionViewModelFactory,
			storeMetadataItem: StoreMetadataItem
		): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
			override fun <T : ViewModel?> create(modelClass: Class<T>): T {
				return assistedFactory.createFileDescriptionViewModel(storeMetadataItem) as T
			}
		}
	}
}
