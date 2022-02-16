package com.shov.unlimstorage.viewModels.files

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shov.unlimstorage.models.repositories.files.NewFileRepository
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import com.shov.unlimstorage.values.argFileName
import com.shov.unlimstorage.values.argFolderId
import com.shov.unlimstorage.values.argStorageType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class UploadFileViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle,
	private val newFileRepository: NewFileRepository,
) : ViewModel() {
	private var fileName by mutableStateOf<String?>(null)
	private var folderId by mutableStateOf<String?>(null)
	var type by mutableStateOf<StorageType?>(null)

	fun uploadFile(file: InputStream?, onFinished: () -> Unit) {
		if ((fileName != null).or(file != null).or(type != null)) {
			viewModelScope.launch(Dispatchers.IO) {
				newFileRepository.uploadFile(file!!, fileName!!, type!!, folderId)
			}.invokeOnCompletion { onFinished() }
		} else throw NullPointerException()
	}

	init {
		savedStateHandle.get<String?>(argStorageType)?.let { type ->
			this.type = StorageType.valueOf(type)
		}
		savedStateHandle.get<String?>(argFolderId)?.let { folderId ->
			this.folderId = folderId
		}
		savedStateHandle.get<String?>(argFileName)?.let { fileName ->
			this.fileName = fileName
		}
	}
}
