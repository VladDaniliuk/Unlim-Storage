package com.shov.filesfeature.views.fileActions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shov.coremodels.models.ItemType
import com.shov.coremodels.models.StorageType
import com.shov.coreui.viewModels.NavigationViewModel
import com.shov.coreutils.utils.observeConnectivityAsFlow
import com.shov.coreutils.values.argFolderName
import com.shov.coreutils.values.argItemType
import com.shov.coreutils.values.argStorageType
import com.shov.coreutils.values.argStoreId
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.filesfeature.R
import com.shov.filesfeature.views.newObject.newFolder.NewFolderView
import com.shov.storagerepositories.repositories.files.FileActionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun FileRenameBottomSheet(
    fileRenameViewModel: FileRenameViewModel = hiltViewModel(),
    navigationViewModer: NavigationViewModel = singletonViewModel()
) {
    val isConnected by LocalContext.current.observeConnectivityAsFlow().collectAsState(false)
    val context = LocalContext.current

    NewFolderView(
        focusRequester = fileRenameViewModel.focusRequester,
        value = fileRenameViewModel.name,
        onValueChange = fileRenameViewModel::onValueChange,
        onTrailingIconClick = { fileRenameViewModel.onValueChange("") },
        onClick = { onError ->
            if (isConnected) {
                fileRenameViewModel.onSubmit(onError) {
                    navigationViewModer.popBack()
                }
            } else {
                fileRenameViewModel.textError =
                    context.getString(R.string.check_connection)
                onError()
            }
        },
        textError = fileRenameViewModel.textError
    )

    LaunchedEffect(key1 = null) {
        fileRenameViewModel.focusRequester.requestFocus()
    }
}

@HiltViewModel
class FileRenameViewModel @Inject constructor(
    private val fileActionsRepository: FileActionsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val focusRequester = FocusRequester()
    var name by mutableStateOf("")
        private set
    var textError by mutableStateOf("")

    fun onValueChange(value: String) {
        name = value
        textError = ""
    }

    fun onSubmit(onError: () -> Unit, onFinish: () -> Unit) {
        if (name.isEmpty()) {
            textError = "Name can't be empty"
            onError()
        } else {
            viewModelScope.launch(Dispatchers.Default) {
                fileActionsRepository.renameFile(
                    ItemType.valueOf(
                        savedStateHandle.get<String>(argItemType)
                            ?: throw IllegalStateException("Item type is null")
                    ),
                    StorageType.valueOf(
                        savedStateHandle.get<String>(argStorageType)
                            ?: throw IllegalStateException("Storage type is null")
                    ),
                    savedStateHandle.get<String>(argStoreId)
                        ?: throw IllegalStateException("Store id is null"),
                    name
                )
            }.invokeOnCompletion { throwable ->
                throwable?.message?.let {
                    textError = it
                    onError()
                } ?: onFinish()
            }
        }
    }

    init {
        name = savedStateHandle.get<String>(argFolderName) ?: ""
    }
}