package com.shov.filesfeature.viewModels.newObject

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
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
import javax.inject.Inject

@HiltViewModel
class NewFolderViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle,
	private val fileActionsRepository: FileActionsRepository
) : ViewModel() {
	var text by mutableStateOf("")
		private set
	var type by mutableStateOf<StorageType?>(null)
	var textError by mutableStateOf("")
	private var folderId by mutableStateOf<String?>(null)
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
				if (fileActionsRepository.createFolder(folderId, text, type)) onCompletion() else {
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
