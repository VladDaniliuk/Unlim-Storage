package com.shov.unlimstorage.viewModels.navigations

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.shov.unlimstorage.models.items.BackStack
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FileListViewModel @Inject constructor() : ViewModel() {
	var backStacks by mutableStateOf<List<BackStack>>(listOf())
		private set

	val isBackHandlerAvailable: Boolean
		get() = backStacks.isNotEmpty()

	fun dropAllFromBackStack() {
		this.backStacks = listOf()
	}

	fun addToBackStack(backStack: BackStack) {
		backStacks = backStacks + backStack
	}

	fun dropFromBackStack(index: Int) {
		backStacks = backStacks.dropLast(backStacks.lastIndex - index)
	}

	fun dropFromBackStack() {
		backStacks = backStacks.dropLast(1)
	}
}
