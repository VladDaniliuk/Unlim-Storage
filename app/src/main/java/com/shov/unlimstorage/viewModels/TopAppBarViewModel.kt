package com.shov.unlimstorage.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopAppBarViewModel @Inject constructor() : ViewModel() {
	var prevRoute by mutableStateOf<Pair<ImageVector, (() -> Unit)>?>(null)
		private set
	var title by mutableStateOf<String?>(null)
		private set
	var nextRoute by mutableStateOf<Pair<ImageVector, (() -> Unit)>?>(null)
		private set

	fun setTopBar(
		prevRoute: Pair<ImageVector, (() -> Unit)>?,
		title: String?,
		nextRoute: Pair<ImageVector, (() -> Unit)>?
	) {
		this.prevRoute = prevRoute
		this.title = title
		this.nextRoute = nextRoute
	}
}