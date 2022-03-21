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
import com.shov.coremodels.models.StoreItem
import com.shov.coremodels.models.StoreMetadataItem
import com.shov.coreutils.values.argStoreId
import com.shov.storagerepositories.repositories.files.FileActionsRepository
import com.shov.storagerepositories.repositories.files.FilesInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FileInfoViewModel @Inject constructor(
	private val filesInfoRepository: FilesInfoRepository,
	private val fileActionsRepository: FileActionsRepository,
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

	fun downloadFile(
		setPercents: (Float, String) -> Unit,
		onStart: () -> Unit,
		onError: () -> Unit
	) {
		setShowDialog(false)

		this.storeMetadata?.let { metadata ->
			this.storeItem?.let { item ->
				metadata.size?.let { size ->
					viewModelScope.launch(Dispatchers.IO) {
						fileActionsRepository.downloadFile(
							item.disk,
							id!!,
							metadata.name,
							size,
							setPercents,
							onStart,
							onError
						)
					}
				} ?: setShowDialog(false)
			} ?: setShowDialog(false)
		} ?: setShowDialog(false)
	}

	init {
		savedStateHandle.get<String>(argStoreId)?.let { id ->
			this.id = id
		} ?: throw NullPointerException()

		viewModelScope.launch {
			filesInfoRepository.getLocalItemAsync(id!!).collectLatest { storeItem ->
				this@FileInfoViewModel.storeItem = storeItem
			}
		}
	}
}
