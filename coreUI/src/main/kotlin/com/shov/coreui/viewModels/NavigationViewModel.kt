package com.shov.coreui.viewModels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor() : ViewModel() {
	val route = MutableSharedFlow<String>(extraBufferCapacity = 1)
	var popUpRoute: String = ""
		get() {
			val result = field
			field = ""
			return result
		}
		private set
	var inclusive: Boolean = false
		get() {
			val result = field
			field = false
			return result
		}
		private set


	fun navigateTo(destination: String, popUp: String = "", inclusive: Boolean = false) {
		popUpRoute = popUp
		this.inclusive = inclusive
		route.tryEmit(destination)
	}

	fun popBack() {
		route.tryEmit("")
	}
}