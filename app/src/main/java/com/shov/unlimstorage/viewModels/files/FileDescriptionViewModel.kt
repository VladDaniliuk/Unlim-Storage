package com.shov.unlimstorage.viewModels.files

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shov.coremodels.StoreItem
import com.shov.unlimstorage.models.repositories.files.FilesInfoRepository
import com.shov.unlimstorage.values.argStoreId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FileDescriptionViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle,
	private val filesInfoRepository: FilesInfoRepository
) : ViewModel() {
	var id by mutableStateOf<String?>(null)
		private set
	var description by mutableStateOf<String?>(null)
		private set
	var storeItem by mutableStateOf<StoreItem?>(null)
		private set

	fun setNewDescription(newDescription: String) {
		description = newDescription
	}

	fun getStoreItem() {
		viewModelScope.launch(Dispatchers.IO) {
			storeItem = filesInfoRepository.getLocalItem(id!!)
		}
	}

	fun getDescription() {
		storeItem?.let { storeItem ->
			viewModelScope.launch(Dispatchers.IO) {
				description = filesInfoRepository.getRemoteMetadata(
					id!!,
					storeItem.disk,
					storeItem.type
				)?.description
			}
		}
	}

	init {
		savedStateHandle.get<String>(argStoreId)?.let { id ->
			this.id = id
		} ?: throw NullPointerException()
	}
}
