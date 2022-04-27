package com.shov.coreui.viewModels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor() : ViewModel() {
	val route = MutableSharedFlow<String>(extraBufferCapacity = 1)

	fun navigateTo(destination: String) {
		route.tryEmit(destination)
	}

	fun popBack() {
		route.tryEmit("")
	}
}