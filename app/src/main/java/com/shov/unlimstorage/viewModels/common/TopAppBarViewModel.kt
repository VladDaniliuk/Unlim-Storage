package com.shov.unlimstorage.viewModels.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import com.shov.unlimstorage.models.items.TopAppBar
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopAppBarViewModel @Inject constructor() : ViewModel() {
	var topAppBar by mutableStateOf(TopAppBar())
		private set

	var prevRouteOld by mutableStateOf<ImageVector?>(null)
		private set
	var nextRouteOld by mutableStateOf<ImageVector?>(null)
		private set

	fun onPrevRouteChange() {
		if (topAppBar.prevRoute != null) prevRouteOld = topAppBar.prevRoute?.first
	}

	fun onNextRouteChange() {
		if (topAppBar.nextRoute != null) nextRouteOld = topAppBar.nextRoute?.first
	}

	fun setTopBar(
		prevRoute: Pair<ImageVector, (() -> Unit)>? = null,
		title: String? = null,
		nextRoute: Pair<ImageVector, (() -> Unit)>? = null
	) {
		topAppBar = TopAppBar(prevRoute, title, nextRoute)
	}
}
