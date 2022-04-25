package com.shov.filesfeature.viewModels.newObject

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shov.storagerepositories.repositories.files.FileActionsRepository
import com.shov.coremodels.models.StorageType
import com.shov.coreutils.values.argFileName
import com.shov.coreutils.values.argFolderId
import com.shov.coreutils.values.argStorageType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class UploadFileViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle,
	private val fileActionsRepository: FileActionsRepository,
) : ViewModel() {
	private var fileName by mutableStateOf<String?>(null)
	private var folderId by mutableStateOf<String?>(null)
	var type by mutableStateOf<StorageType?>(null)

	fun uploadFile(file: InputStream?, onFinished: () -> Unit) {
		if ((fileName != null).or(file != null).or(type != null)) {
			viewModelScope.launch(Dispatchers.IO) {
				fileActionsRepository.uploadFile(file!!, fileName!!, type!!, folderId)
			}.invokeOnCompletion { onFinished() }
		} else throw NullPointerException()
	}

	init {
		savedStateHandle.get<String?>(argStorageType)?.let { type ->
			if (type.isNotEmpty()) this.type = StorageType.valueOf(type)
		}
		savedStateHandle.get<String?>(argFolderId)?.let { folderId ->
			this.folderId = folderId
		}
		savedStateHandle.get<String?>(argFileName)?.let { fileName ->
			this.fileName = fileName
		}
	}
}
