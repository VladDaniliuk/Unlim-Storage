package com.shov.filesfeature.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DownloadViewModel @Inject constructor() : ViewModel() {
	//TODO Rewrite and may be delete
	var title by mutableStateOf("")
		private set

	fun onDownload(title: String) {
		this.title = title
	}
}
