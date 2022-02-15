package com.shov.unlimstorage.viewModels.navigations

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.shov.unlimstorage.models.items.BackStack
import com.shov.unlimstorage.utils.getBackStack
import com.shov.unlimstorage.utils.popBackStack
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FileListViewModel @Inject constructor() : ViewModel() {
	var backStacks by mutableStateOf<List<BackStack>>(listOf())
		private set

	val isBackHandlerAvailable: Boolean
		get() = backStacks.isNotEmpty()

	fun dropAllFromBackStack(navHostController: NavHostController) {
		navHostController.popBackStack(backStacks.size)
		this.backStacks = listOf()
	}

	fun dropFromBackStack(navHostController: NavHostController, index: Int? = null) {
		navHostController.popBackStack(index?.let { backStacks.lastIndex - index } ?: 1)
		backStacks = backStacks.dropLast(index?.let { backStacks.lastIndex - index } ?: 1)
	}

	fun compareBackStack(navHostController: NavHostController) {
		navHostController.getBackStack()?.let { backStack ->
			if (backStack != backStacks.lastOrNull()) {
				this.backStacks = this.backStacks + backStack
			}
		}
	}
}