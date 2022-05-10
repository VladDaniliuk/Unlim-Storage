package com.shov.coreui.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import com.shov.coreui.models.TopAppBar
import com.shov.coreui.models.TopAppBarStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopAppBarViewModel @Inject constructor() : ViewModel() {
	val focusRequester = FocusRequester()

	//TopAppBar
	var topAppBar by mutableStateOf(TopAppBar())
		private set

	var searchText by mutableStateOf("")
		private set

	fun setSearch(text: String) {
		searchText = text
	}

	fun setTopBar(
		prevRoute: Pair<ImageVector, (() -> Unit)>? = null,
		title: String? = null,
		nextRoute: Pair<ImageVector, (() -> Unit)>? = null,
		status: TopAppBarStatus = TopAppBarStatus.Title
	) {
		topAppBar = TopAppBar(prevRoute, title, nextRoute, status)
	}
}
