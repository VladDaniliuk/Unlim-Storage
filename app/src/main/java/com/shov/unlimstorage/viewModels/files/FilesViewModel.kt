package com.shov.unlimstorage.viewModels.files

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shov.unlimstorage.models.items.BackStack
import com.shov.unlimstorage.models.items.ItemType
import com.shov.unlimstorage.models.items.StoreItem
import com.shov.unlimstorage.models.repositories.files.FilesRepository
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilesViewModel @Inject constructor(
	private val filesRepository: FilesRepository,
) : ViewModel() {
	var storeItemList by mutableStateOf(emptyList<StoreItem>())
		private set
	var isRefreshing by mutableStateOf(false)
		private set
	var isClickable by mutableStateOf(true)
		private set

	fun navigate(
		folderName: String,
		id: String,
		itemType: ItemType,
		storageType: StorageType,
		navigateToFileInfo: (String) -> Unit,
		navigateToFolder: (BackStack) -> Unit
	) {
		isClickable = false

		storeItemList = emptyList()

		when (itemType) {
			ItemType.FILE -> navigateToFileInfo(id)
			ItemType.FOLDER -> {
				navigateToFolder(BackStack(id, storageType, folderName))
			}
		}

		isClickable = true
	}

	fun onRefresh(
		isConnected: Boolean,
		folderId: String?,
		storageType: StorageType?,
		onConnectionFailed: () -> Unit
	) {
		if (isConnected) {
			isRefreshing = true

			viewModelScope.launch(Dispatchers.IO) {
				storeItemList = filesRepository.checkRemote(folderId, storageType)
			}.invokeOnCompletion {
				isRefreshing = false
			}
		} else onConnectionFailed()
	}

	fun onConnectionChange(isConnected: Boolean, backStack: BackStack?) {
		isRefreshing = true

		viewModelScope.launch(Dispatchers.IO) {
			storeItemList = if (isConnected) {
				filesRepository.checkLocal(backStack?.folderId, backStack?.storageType)
			} else {
				filesRepository.getFromLocal(backStack?.folderId)
			}
		}.invokeOnCompletion {
			isRefreshing = false
		}
	}
}
