package com.shov.dropboxstorage.viewmodels

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import com.dropbox.core.android.Auth
import com.dropbox.core.oauth.DbxCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class DropBoxAuthViewModel @Inject constructor() : ViewModel() {
	private var isShown = false

	fun onLaunched(onLaunched: () -> Unit) {
		if (isShown.not()) {
			isShown = true

			onLaunched()
		}
	}

	fun onResumed(
		event: Lifecycle.Event,
		onFinish: () -> Unit,
		onHasDbxCredential: (DbxCredential) -> Unit
	) {
		if ((event == Lifecycle.Event.ON_RESUME) and isShown) {
			Auth.getDbxCredential()?.let(onHasDbxCredential)

			onFinish()
		}
	}
}