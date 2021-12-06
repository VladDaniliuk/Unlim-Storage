package com.shov.unlimstorage.models.repositories.signIn

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.dropbox.core.android.Auth
import com.shov.unlimstorage.models.preferences.Preference
import com.shov.unlimstorage.values.DROPBOX_CREDENTIAL
import com.shov.unlimstorage.values.NEVER_ACCESSED

@Composable
fun CheckDropboxCredential(
	additionalCheck: Boolean = true,
	lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
	context: Context = LocalContext.current,
	onGetCredential: () -> Unit
) {
	DisposableEffect(key1 = null) {
		@Suppress(NEVER_ACCESSED)
		val observer = LifecycleEventObserver { _, event ->
			if ((event == Lifecycle.Event.ON_RESUME) and additionalCheck) {
				var credentialPref by Preference(context, DROPBOX_CREDENTIAL, "")

				val credential = Auth.getDbxCredential()

				credential?.let {
					credentialPref = credential.toString()
					onGetCredential()
				}
			}
		}

		lifecycleOwner.lifecycle.addObserver(observer)

		onDispose {
			lifecycleOwner.lifecycle.removeObserver(observer)
		}
	}
}