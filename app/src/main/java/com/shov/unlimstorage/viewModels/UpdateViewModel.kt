package com.shov.unlimstorage.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.shov.unlimstorage.BuildConfig
import com.shov.unlimstorage.api.models.LastReleaseItem
import com.shov.unlimstorage.models.UpdateViewModelFactory
import com.shov.unlimstorage.models.preferences.Preference
import com.shov.unlimstorage.models.repositories.GitHubRepository
import com.shov.unlimstorage.values.UNCHECKED_CAST
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class UpdateViewModel @AssistedInject constructor(
	private val gitHubRepository: GitHubRepository,
	@Assisted var isShowAgainPreference: Preference<Boolean>
) : ViewModel() {
	var lastRelease by mutableStateOf<LastReleaseItem?>(null)
		private set
	var isDialogShown by mutableStateOf(false)
		private set
	private var _isShowAgain by isShowAgainPreference
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
			if (element.toLong() > currentVersion.getOrNull(i)?.toLong() ?: 0) {
				isDialogShown = true
				return
			} else if (element.toLong() < currentVersion.getOrNull(i)?.toLong() ?: 0) {
				return
			}
		}
	}

	fun hideDialog() {
		isDialogShown = false
	}

	@Suppress(UNCHECKED_CAST)
	companion object {
		fun provideFactory(
			assistedFactory: UpdateViewModelFactory,
			isShowAgain: Preference<Boolean>
		): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
			override fun <T : ViewModel?> create(modelClass: Class<T>): T {
				return assistedFactory.createUpdateViewModel(isShowAgain) as T
			}
		}
	}
}
