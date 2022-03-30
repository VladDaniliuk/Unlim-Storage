package com.shov.unlimstorage.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DownloadViewModel @Inject constructor() : ViewModel() {
	var title by mutableStateOf("")
		private set
	var percents by mutableStateOf(0f)
		private set

	fun setProgress(percents: Float, title: String) {
		this.percents = percents
		this.title = title
	}
}
