package com.shov.unlimstorage.viewModels.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shov.unlimstorage.BuildConfig
import com.shov.unlimstorage.api.models.LastReleaseItem
import com.shov.unlimstorage.models.repositories.GitHubRepository
import com.shov.unlimstorage.models.repositories.PreferenceRepository
import com.shov.unlimstorage.values.IS_UPDATE_SHOW
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(
	private val gitHubRepository: GitHubRepository,
	preference: PreferenceRepository
) : ViewModel() {
	var lastRelease by mutableStateOf<LastReleaseItem?>(null)
		private set
	var isDialogShown by mutableStateOf(false)
		private set
	private var _isShowAgain by preference.getPref(IS_UPDATE_SHOW, true)
	var isShowAgain by mutableStateOf(_isShowAgain)
		private set

	fun setShowDialogAgain() {
		_isShowAgain = _isShowAgain.not()
		isShowAgain = _isShowAgain
	}

	fun checkAppVersion() {
		viewModelScope.launch {
			gitHubRepository.getLastRelease().apply {
				if (this.isSuccessful) {
					lastRelease = this.body()

					this.body()?.let { lastReleaseItem ->
						checkNeedUpdate(lastReleaseItem.version)
					}
				}
			}
		}
	}

	private fun checkNeedUpdate(lastReleaseVersion: String) {
		val currentVersion = BuildConfig.VERSION_NAME.split(".")

		lastReleaseVersion.split(".").forEachIndexed { i, element ->
			if (element.toLong() > (currentVersion.getOrNull(i)?.toLong() ?: 0)) {
				isDialogShown = true
				return
			} else if (element.toLong() < (currentVersion.getOrNull(i)?.toLong() ?: 0)) {
				return
			}
		}
	}

	fun hideDialog() {
		isDialogShown = false
	}
}
