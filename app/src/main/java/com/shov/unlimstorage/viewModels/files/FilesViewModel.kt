package com.shov.unlimstorage.viewModels.files

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shov.unlimstorage.models.items.BackStack
import com.shov.coremodels.ItemType
import com.shov.coremodels.StoreItem
import com.shov.unlimstorage.models.repositories.files.FilesInfoRepository
import com.shov.coremodels.StorageType
import com.shov.unlimstorage.values.Screen
import com.shov.unlimstorage.values.argFolderId
import com.shov.unlimstorage.values.argStorageType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
	var storeItemList by mutableStateOf(emptyList<StoreItem>())
		private set
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
			launch {
				storeItemList = filesInfoRepository.checkRemote(folderId, storageType)
			}
		} else {
			onConnectionFailed() } }

	fun onConnectionChange(isConnected: Boolean) {
		launch {
			storeItemList = if (isConnected) {
				filesInfoRepository.checkLocal(folderId, storageType)
			} else {
				filesInfoRepository.getFromLocal(folderId)
			}
		}
	}

	private fun launch(block: suspend CoroutineScope.() -> Unit) {
		isRefreshing = true

		viewModelScope.launch(
			context = Dispatchers.IO,
			block = block
		).invokeOnCompletion {
			isRefreshing = false
		}
	}

	init {
		savedStateHandle.get<String?>(argFolderId)?.let { folderId ->
			this.folderId = folderId
		}
		savedStateHandle.get<String?>(argStorageType)?.let { storageType ->
			this.storageType = StorageType.valueOf(storageType)
		}
	}
}
