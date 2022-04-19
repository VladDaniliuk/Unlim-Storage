package com.shov.filesfeature.viewModels

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.shov.coremodels.models.StoreItem
import com.shov.coreutils.values.argStoreId
import com.shov.storagerepositories.repositories.files.FilesInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FileActionsViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle,
	filesInfoRepository: FilesInfoRepository
) : ViewModel() {
	private val storeItemFlow: Flow<StoreItem>

	val storeItem: State<StoreItem>
		@Composable
		get():State<StoreItem> = storeItemFlow.collectAsState(initial = StoreItem())

	init {
		storeItemFlow = filesInfoRepository.getLocalItemAsync(savedStateHandle[argStoreId]!!)
	}
}