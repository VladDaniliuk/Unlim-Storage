package com.shov.unlimstorage.viewModels.files

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shov.unlimstorage.models.items.StoreItem
import com.shov.unlimstorage.models.repositories.files.FilesRepository
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilesViewModel @Inject constructor(
	private val filesRepository: FilesRepository
) : ViewModel() {
	private var _storeItemList by mutableStateOf(emptyList<StoreItem>())
	val storeItemList get() = _storeItemList

	private var _isRefreshing by mutableStateOf(false)
	val isRefreshing get() = _isRefreshing

	private var _isClickable by mutableStateOf(true)
	val isClickable get() = _isClickable

	fun setClickable(isClickable: Boolean) {
		_isClickable = isClickable
	}

	fun getFiles(folderId: String? = null, storageType: StorageType? = null) {
		_isRefreshing = true
		viewModelScope.launch(Dispatchers.IO) {
			_storeItemList = filesRepository.checkLocal(
				parentFolder = folderId,
				disk = storageType
			)
		}.invokeOnCompletion {
			_isRefreshing = false
		}
	}

	fun refreshFiles(folderId: String? = null, storageType: StorageType? = null) {
		_isRefreshing = true
		viewModelScope.launch(Dispatchers.IO) {
			_storeItemList = filesRepository.checkRemote(
				parentFolder = folderId,
				disk = storageType
			)
		}.invokeOnCompletion {
			_isRefreshing = false
		}
	}

	fun checkLocalFiles(folderId: String? = null) {
		_isRefreshing = true
		viewModelScope.launch(Dispatchers.IO) {
			_storeItemList = filesRepository.getFromLocal(parentFolder = folderId)
		}.invokeOnCompletion {
			_isRefreshing = false
		}
	}
}
