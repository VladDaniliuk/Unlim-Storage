package com.shov.filesfeature.viewModels.newObject

import android.os.ParcelFileDescriptor
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shov.coremodels.models.StorageType
import com.shov.coreutils.values.argFolderId
import com.shov.coreutils.values.argStorageType
import com.shov.storagerepositories.repositories.files.FileActionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.FileInputStream
import javax.inject.Inject

@HiltViewModel
class UploadFileViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle,
	private val fileActionsRepository: FileActionsRepository
) : ViewModel() {
	private var folderId by mutableStateOf<String?>(null)
	var type by mutableStateOf<StorageType?>(null)

	fun uploadFile(fileDescriptor: ParcelFileDescriptor?, name: String, onSuccess: () -> Unit) {
		viewModelScope.launch(Dispatchers.IO) {
			fileActionsRepository.uploadFile(
				FileInputStream(fileDescriptor?.fileDescriptor),
				name,
				type!!,
				folderId
			)
		}.invokeOnCompletion {
			fileDescriptor?.close()

			viewModelScope.launch(Dispatchers.Main) {
				onSuccess()
			}
		}
	}

	init {
		savedStateHandle.get<String?>(argStorageType)?.let { type ->
			if (type.isNotEmpty()) this.type = StorageType.valueOf(type)
		}
		savedStateHandle.get<String?>(argFolderId)?.let { folderId ->
			if (folderId.isNotEmpty()) this.folderId = folderId
		}
	}
}
