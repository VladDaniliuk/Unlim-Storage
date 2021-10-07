package com.shov.unlimstorage.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shov.unlimstorage.models.filesRepository.FilesRepository
import com.shov.unlimstorage.models.items.StoreItem
import com.shov.unlimstorage.models.signInModels.StorageType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilesViewModel @Inject constructor(
	private val filesRepository: FilesRepository
) : ViewModel() {
	private val _storeItemList = MutableStateFlow(listOf<StoreItem>())
	val storeItemList = _storeItemList.asStateFlow()

	private val _isRefreshing = MutableStateFlow(false)
	val isRefreshing = _isRefreshing.asStateFlow()

	fun getFiles(folderId: String? = null, storageType: StorageType? = null) {
		_isRefreshing.value = true
		viewModelScope.launch(Dispatchers.IO) {
			_storeItemList.value =
				filesRepository.checkLocal(
					parentFolder = folderId,
					disk = storageType
				)
		}.invokeOnCompletion {
			_isRefreshing.value = false
		}
	}

	fun refreshFiles(folderId: String? = null, storageType: StorageType? = null) {
		_isRefreshing.value = true
		viewModelScope.launch(Dispatchers.IO) {
			_storeItemList.value = filesRepository.checkRemote(
				parentFolder = folderId,
				disk = storageType
			)
		}.invokeOnCompletion {
			_isRefreshing.value = false
		}
	}

	fun checkLocalFiles(folderId: String? = null) {
		_isRefreshing.value = true
		viewModelScope.launch(Dispatchers.IO) {
			_storeItemList.value =
				filesRepository.getFromLocal(parentFolder = folderId)
		}.invokeOnCompletion {
			_isRefreshing.value = false
		}
	}
}
