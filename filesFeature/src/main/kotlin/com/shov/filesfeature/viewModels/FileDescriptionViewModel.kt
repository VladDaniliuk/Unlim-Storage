package com.shov.filesfeature.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shov.coremodels.models.StoreItem
import com.shov.coreutils.values.argStoreId
import com.shov.storagerepositories.repositories.files.FilesInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FileDescriptionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val filesInfoRepository: FilesInfoRepository
) : ViewModel() {
    val id = savedStateHandle.get<String>(argStoreId) ?: throw NullPointerException()
    var description by mutableStateOf<String?>(null)
        private set
    var storeItem by mutableStateOf<StoreItem?>(null)
        private set

    fun setNewDescription(newDescription: String) {
        description = newDescription
    }

    fun getDescription() {
        storeItem?.let { storeItem ->
            viewModelScope.launch(Dispatchers.IO) {
                description = filesInfoRepository.getRemoteMetadata(
                    id,
                    storeItem.disk,
                    storeItem.type
                )?.description
            }
        }
    }

    fun setDescription(onComplete: () -> Unit) {
        storeItem?.let { storeItem ->
            viewModelScope.launch {
                filesInfoRepository.changeDescription(
                    storeItem.disk,
                    storeItem.id,
                    description ?: ""
                )
            }.invokeOnCompletion {
                onComplete()
            }
        }
    }

    init {
        viewModelScope.launch {
            filesInfoRepository.getLocalItemAsync(id).collectLatest { storeItem ->
                this@FileDescriptionViewModel.storeItem = storeItem
            }
        }
    }
}
