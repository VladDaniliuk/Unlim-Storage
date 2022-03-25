package com.shov.unlimstorage.viewModels

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shov.unlimstorage.models.repositories.DownloadRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DownloadViewModel @Inject constructor(
	private val downloadRepository: DownloadRepository
) : ViewModel() {
	private var _downloadId by mutableStateOf<Long?>(null)
	var title by mutableStateOf("")
		private set
	var percents by mutableStateOf(0f)
		private set

	fun subscribeToDownload(url: String, name: String, version: String) {
		downloadRepository.downloadFile(Uri.parse(url), name, version)?.let { id ->
			_downloadId = id

			viewModelScope.launch {
				downloadRepository.checkDownload(id, name, ::setProgress)
			}
		}
	}

	fun setProgress(percents: Float, title: String) {
		this.percents = percents
		this.title = title
	}

	fun dismissDownloading() {
		_downloadId?.let { id ->
			downloadRepository.dismissDownloading(id) { percents ->
				setProgress(percents, title)
			}
		}
	}
}
