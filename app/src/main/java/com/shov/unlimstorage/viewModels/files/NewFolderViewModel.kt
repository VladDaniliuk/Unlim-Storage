package com.shov.unlimstorage.viewModels.files

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shov.unlimstorage.models.repositories.files.FilesRepository
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import com.shov.unlimstorage.values.argFolderId
import com.shov.unlimstorage.values.argStorageType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewFolderViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle,
	private val filesRepository: FilesRepository
) : ViewModel() {
	var text by mutableStateOf("")
		private set
	var type by mutableStateOf<StorageType?>(null)
	var textError by mutableStateOf("")
	var folderId by mutableStateOf<String?>(null)
		private set
	val focusRequester = FocusRequester()

	fun onTextChange(text: String = "") {
		this.text = text
		textError = ""
	}

	fun createFolder(
		onCompletion: () -> Unit,
		textError: String,
		onError: () -> Unit
	) {
		viewModelScope.launch(Dispatchers.IO) {
			type?.let { type ->
				if (filesRepository.createFolder(folderId, text, type)) onCompletion() else {
					this@NewFolderViewModel.textError = textError
					onError()
				}
			}
		}
	}


	init {
		savedStateHandle.get<String?>(argStorageType)?.let { type ->
			this.type = StorageType.valueOf(type)
		}
		savedStateHandle.get<String?>(argFolderId)?.let { folderId ->
			this.folderId = folderId
		}
	}
}
