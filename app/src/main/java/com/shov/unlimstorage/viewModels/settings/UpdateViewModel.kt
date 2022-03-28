package com.shov.unlimstorage.viewModels.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shov.autoupdatefeature.models.LastReleaseItem
import com.shov.preferences.datasources.PreferencesDataSource
import com.shov.preferences.values.IS_UPDATE_SHOW
import com.shov.unlimstorage.BuildConfig
import com.shov.unlimstorage.models.repositories.GitHubRepository
import com.shov.unlimstorage.utils.compareWithOld
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(
	private val gitHubRepository: GitHubRepository,
	preferences: PreferencesDataSource
) : ViewModel() {
	var lastRelease by mutableStateOf<LastReleaseItem?>(null)
		private set
	var wasDialogShown by mutableStateOf(false)
		private set
	var isDialogShown by mutableStateOf(false)
		private set
	private var _isShowAgain by preferences.getPref(IS_UPDATE_SHOW, true)
	var isShowAgain by mutableStateOf(_isShowAgain)
		private set

	fun setShowDialogAgain() {
		_isShowAgain = _isShowAgain.not()
		isShowAgain = _isShowAgain
	}

	fun checkAppVersion() {
		viewModelScope.launch {
			lastRelease = gitHubRepository.getLastRelease().body()

			lastRelease?.tagName?.compareWithOld(
				BuildConfig.VERSION_NAME,
				onNewerAction = {
					isDialogShown = true
					wasDialogShown = true
				}
			)
		}
	}

	fun hideDialog() {
		isDialogShown = false
	}
}
