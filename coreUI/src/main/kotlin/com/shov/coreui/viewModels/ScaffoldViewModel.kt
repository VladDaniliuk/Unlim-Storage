package com.shov.coreui.viewModels

import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shov.coreui.models.TopAppBar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScaffoldViewModel @Inject constructor() : ViewModel() {
	val scaffoldState = ScaffoldState(DrawerState(DrawerValue.Closed), SnackbarHostState())

	fun showSnackbar(message: String) {
		viewModelScope.launch {
			scaffoldState.snackbarHostState.showSnackbar(message)
		}
	}

	var topAppBar by mutableStateOf(TopAppBar())
		private set

	private var prevRouteOld by mutableStateOf<ImageVector?>(null)
	private var nextRouteOld by mutableStateOf<ImageVector?>(null)

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
