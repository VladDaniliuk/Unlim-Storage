package com.shov.dropboxstorage.views

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.android.Auth
import com.shov.dropboxstorage.values.CLIENT_IDENTIFIER
import com.shov.dropboxstorage.values.DROPBOX_CLIENT_ID
import com.shov.dropboxstorage.values.DROPBOX_CREDENTIAL
import com.shov.dropboxstorage.viewmodels.DropBoxAuthViewModel

@Composable
internal fun DropBoxAuthScreen(
	dropBoxAuthViewModel: DropBoxAuthViewModel = viewModel(),
	context: Context = LocalContext.current,
	lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
) {
	LaunchedEffect(key1 = null) {
		dropBoxAuthViewModel.onLaunched {
			Auth.startOAuth2PKCE(
				context,
				DROPBOX_CLIENT_ID,
				DbxRequestConfig(CLIENT_IDENTIFIER)
			)
		}
	}

	DisposableEffect(key1 = null) {
		val observer = LifecycleEventObserver { _, event ->
			dropBoxAuthViewModel.onResumed(
				event,
				onFinish = { (context as ComponentActivity).finish() }
			) { credential ->
				(context as ComponentActivity).setResult(
					ComponentActivity.RESULT_OK,
					Intent().putExtra(DROPBOX_CREDENTIAL, credential.toString())
				)
			}
		}

		lifecycle.addObserver(observer)

		onDispose {
			lifecycle.removeObserver(observer)
		}
	}
}