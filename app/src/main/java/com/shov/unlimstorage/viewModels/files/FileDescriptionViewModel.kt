package com.shov.unlimstorage.viewModels.files

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.shov.unlimstorage.models.FileDescriptionViewModelFactory
import com.shov.unlimstorage.models.items.StoreItem
import com.shov.unlimstorage.models.repositories.files.FilesRepository
import com.shov.unlimstorage.values.UNCHECKED_CAST
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FileDescriptionViewModel @AssistedInject constructor(
	@Assisted private val storeId: String,
	private val filesRepository: FilesRepository
) : ViewModel() {
	var description by mutableStateOf<String?>(null)
		private set
	var storeItem by mutableStateOf<StoreItem?>(null)
		private set

	fun setNewDescription(newDescription: String) {
		description = newDescription
	}

	fun getStoreItem() {
		viewModelScope.launch(Dispatchers.IO) {
			storeItem = filesRepository.getLocalItem(storeId)
		}
	}

	fun getDescription() {
		storeItem?.let { storeItem ->
			viewModelScope.launch(Dispatchers.IO) {
				description = filesRepository.getRemoteMetadata(
					storeId,
					storeItem.disk,
					storeItem.type
				)?.description
			}
		}
	}

	@Suppress(UNCHECKED_CAST)
	companion object {
		fun provideFactory(
			assistedFactory: FileDescriptionViewModelFactory,
			storeId: String
		): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
			override fun <T : ViewModel?> create(modelClass: Class<T>): T {
				return assistedFactory.createFileDescriptionViewModel(storeId) as T
			}
		}
	}
}
