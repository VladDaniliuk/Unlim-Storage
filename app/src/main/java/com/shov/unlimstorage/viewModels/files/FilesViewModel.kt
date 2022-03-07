package com.shov.unlimstorage.viewModels.files

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shov.coremodels.models.ItemType
import com.shov.coremodels.models.StorageType
import com.shov.coremodels.models.StoreItem
import com.shov.storagerepositories.repositories.files.FilesInfoRepository
import com.shov.unlimstorage.models.items.BackStack
import com.shov.unlimstorage.values.Screen
import com.shov.unlimstorage.values.argFolderId
import com.shov.unlimstorage.values.argStorageType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilesViewModel @Inject constructor(
	private val filesInfoRepository: FilesInfoRepository,
	savedStateHandle: SavedStateHandle
) : ViewModel() {
	var folderId by mutableStateOf<String?>(null)
		private set
	var storageType by mutableStateOf<StorageType?>(null)
		private set
	private val storeItemsAsync = MutableStateFlow<List<StoreItem>>(emptyList())
	val storeItems: List<StoreItem>
		get() = storeItemsAsync.value
	var isRefreshing by mutableStateOf(false)
		private set
	var isClickable by mutableStateOf(true)
		private set

	fun onStoreItemClick(
		storeItem: StoreItem,
		onFolderOpen: (BackStack) -> Unit,
		onFileInfoOpen: (String) -> Unit
	) {
		isClickable = false

		when (storeItem.type) {
			ItemType.FILE -> onFileInfoOpen(Screen.FileInfo.setStoreItem(storeItem.id))
			ItemType.FOLDER -> onFolderOpen(
				BackStack(storeItem.id, storeItem.disk.name, storeItem.name)
			)
		}

		isClickable = true
	}

	fun onRefresh(isConnected: Boolean, onConnectionFailed: () -> Unit) {
		if (isConnected) {
			isRefreshing = true

			viewModelScope.launch(Dispatchers.IO) {
				filesInfoRepository.getFromRemote(storageType, folderId)
			}.invokeOnCompletion {
				isRefreshing = false
			}
		} else {
			onConnectionFailed()
		}
	}

	init {
		savedStateHandle.get<String?>(argFolderId)?.let { folderId ->
			this.folderId = folderId
		}
		savedStateHandle.get<String?>(argStorageType)?.let { storageType ->
			this.storageType = StorageType.valueOf(storageType)
		}

		viewModelScope.launch {
			filesInfoRepository.getFromLocalAsync(folderId).collectLatest {
				storeItemsAsync.emit(it)
			}
		}
	}
}
