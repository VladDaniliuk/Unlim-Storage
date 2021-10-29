package com.shov.unlimstorage.viewModels.files

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.shov.unlimstorage.models.FileInfoViewModelFactory
import com.shov.unlimstorage.models.items.StoreItem
import com.shov.unlimstorage.models.items.StoreMetadataItem
import com.shov.unlimstorage.models.repositories.files.FilesRepository
import com.shov.unlimstorage.values.UNCHECKED_CAST
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FileInfoViewModel @AssistedInject constructor(
	private val filesRepository: FilesRepository,
	@Assisted private val id: String
) : ViewModel() {
	private var _storeMetadata by mutableStateOf<StoreMetadataItem?>(null)
	val storeMetadata get() = _storeMetadata

	private var _storeItem by mutableStateOf<StoreItem?>(null)
	val storeItem get() = _storeItem

	fun getFileMetadata() {
		storeItem?.let { item ->
			viewModelScope.launch(Dispatchers.IO) {
				_storeMetadata =
					filesRepository.getRemoteMetadata(item.id, item.disk, item.type)
			}
		}
	}

	fun getStoreItem() {
		viewModelScope.launch(Dispatchers.IO) {
			_storeItem = filesRepository.getLocalItem(id)
		}
	}

	@Suppress(UNCHECKED_CAST)
	companion object {
		fun provideFactory(
			assistedFactory: FileInfoViewModelFactory,
			id: String
		): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
			override fun <T : ViewModel?> create(modelClass: Class<T>): T {
				return assistedFactory.createFileInfoViewModel(id) as T
			}
		}
	}
}
