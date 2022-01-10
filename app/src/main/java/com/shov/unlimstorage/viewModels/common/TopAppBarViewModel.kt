package com.shov.unlimstorage.viewModels.common

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

	var prevRouteOld by mutableStateOf<Pair<ImageVector, (() -> Unit)>?>(null)
		private set
	var nextRouteOld by mutableStateOf<Pair<ImageVector, (() -> Unit)>?>(null)
		private set

	fun onPrevRouteChange() {
		if (prevRoute != null) prevRouteOld = prevRoute
	}

	fun onNextRouteChange() {
		if (nextRoute != null) nextRouteOld = nextRoute
	}

	fun setTopBar(
		prevRoute: Pair<ImageVector, (() -> Unit)>? = null,
		title: String? = null,
		nextRoute: Pair<ImageVector, (() -> Unit)>? = null
	) {
		this.prevRoute = prevRoute
		this.title = title
		this.nextRoute = nextRoute
	}
}
