package com.shov.unlimstorage.viewModels.files

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shov.unlimstorage.models.items.BackStack
import com.shov.unlimstorage.models.items.ItemType
import com.shov.unlimstorage.models.items.StoreItem
import com.shov.unlimstorage.models.repositories.files.FilesRepository
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import com.shov.unlimstorage.values.Screen
import com.shov.unlimstorage.values.argFolderId
import com.shov.unlimstorage.values.argStorageType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilesViewModel @Inject constructor(
	private val filesRepository: FilesRepository,
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

	fun getFiles() {
		isRefreshing = true

		viewModelScope.launch(Dispatchers.IO) {
			storeItemList = filesRepository.checkLocal(folderId, storageType)
		}.invokeOnCompletion {
			isRefreshing = false
		}
	}

	fun navigate(
		folderName: String,
		id: String,
		itemType: ItemType,
		storageType: StorageType,
		navigateTo: (String) -> Unit
	) {
		isClickable = false

		when (itemType) {
			ItemType.FILE -> navigateTo(Screen.FileInfo.setStoreItem(id))
			ItemType.FOLDER -> navigateTo(
				Screen.Files.openFolder(BackStack(id, storageType.name, folderName))
			)
		}

		isClickable = true
	}

	fun refreshFiles() {
		isRefreshing = true

		viewModelScope.launch(Dispatchers.IO) {
			storeItemList = filesRepository.checkRemote(folderId, storageType)
		}.invokeOnCompletion {
			isRefreshing = false
		}
	}

	fun checkLocalFiles() {
		isRefreshing = true

		viewModelScope.launch(Dispatchers.IO) {
			storeItemList = filesRepository.getFromLocal(folderId)
		}.invokeOnCompletion {
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
