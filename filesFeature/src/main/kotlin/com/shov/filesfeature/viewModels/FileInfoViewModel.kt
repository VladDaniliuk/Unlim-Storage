package com.shov.filesfeature.viewModels

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
import com.shov.filesfeature.R
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
    var storeItem by mutableStateOf<StoreItem?>(null)
        private set
    var storeMetadata by mutableStateOf<StoreMetadataItem?>(null)
        private set

    val staredIcon: ImageVector
        get() = Icons.Rounded.run { if (storeMetadata?.isStarred == true) Star else StarBorder }

    fun getFileMetadata() {
        this.storeItem?.let { item ->
            viewModelScope.launch(Dispatchers.IO) {
                storeMetadata = filesInfoRepository.getRemoteMetadata(id!!, item.disk, item.type)
            }
        }
    }

    fun downloadFile(onShowSnackbar: (message: Int) -> Unit) {
        this.storeItem?.let { item ->
            viewModelScope.launch(Dispatchers.IO) {
                onShowSnackbar(R.string.download_started)

                fileActionsRepository.download(
                    storageType = item.disk,
                    id = item.id,
                    name = item.name,
                    type = item.type
                )
            }
        }
    }

    fun deleteFile(goBack: () -> Unit) {
        viewModelScope.launch(Dispatchers.Default) {
            fileActionsRepository.deleteFile(storeItem!!.type, storeItem!!.disk, storeItem!!.id)
        }.invokeOnCompletion {
            goBack()
        }
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
