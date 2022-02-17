package com.shov.unlimstorage.viewModels.files

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shov.coremodels.StoreItem
import com.shov.coremodels.StoreMetadataItem
import com.shov.unlimstorage.models.repositories.files.FilesInfoRepository
import com.shov.unlimstorage.values.argStoreId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FileInfoViewModel @Inject constructor(
	private val filesInfoRepository: FilesInfoRepository,
	savedStateHandle: SavedStateHandle
) : ViewModel() {
	var id by mutableStateOf<String?>(null)
		private set
	var isDialogShown by mutableStateOf(false)
		private set
	var storeItem by mutableStateOf<StoreItem?>(null)
		private set
	var storeMetadata by mutableStateOf<StoreMetadataItem?>(null)
		private set

	fun setShowDialog(isShow: Boolean = true) {
		isDialogShown = isShow
	}

	val staredIcon: ImageVector
		get() = Icons.Rounded.run { if (storeMetadata?.isStarred == true) Star else StarBorder }

	fun getFileMetadata() {
		this.storeItem?.let { item ->
			viewModelScope.launch(Dispatchers.IO) {
				storeMetadata = filesInfoRepository.getRemoteMetadata(id!!, item.disk, item.type)
			}
		}
	}

	fun downloadFile(setPercents: (Float, String) -> Unit) {
		setShowDialog(false)

		this.storeMetadata?.let { metadata ->
			this.storeItem?.let { item ->
				metadata.size?.let { size ->
					viewModelScope.launch(Dispatchers.IO) {
						filesInfoRepository.downloadFile(
							item.disk,
							id!!,
							metadata.name,
							size,
							setPercents
						)
					}
				} ?: setShowDialog(false)
			} ?: setShowDialog(false)
		} ?: setShowDialog(false)
	}

	fun getStoreItem() {
		this.storeItem = filesInfoRepository.getLocalItem(id!!)
	}

	init {
		savedStateHandle.get<String>(argStoreId)?.let { id ->
			this.id = id
		} ?: throw NullPointerException()
	}
}
