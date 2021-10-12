package com.shov.unlimstorage.viewModels.files

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shov.unlimstorage.models.FileActionViewModelFactory
import com.shov.unlimstorage.models.items.StoreItem
import com.shov.unlimstorage.values.UNCHECKED_CAST
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class FileActionsViewModel @AssistedInject constructor(
	@Assisted private val item: StoreItem
) : ViewModel() {
	private var _storeItem by mutableStateOf(item)
	val storeItem get() = _storeItem

	@Suppress(UNCHECKED_CAST)
	companion object {
		fun provideFactory(
			assistedFactory: FileActionViewModelFactory,
			storeItem: StoreItem
		): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
			override fun <T : ViewModel?> create(modelClass: Class<T>): T {
				return assistedFactory.createFileActionsViewModelFactory(storeItem) as T
			}
		}
	}
}
