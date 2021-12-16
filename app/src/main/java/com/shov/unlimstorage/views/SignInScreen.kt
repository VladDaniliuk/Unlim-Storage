package com.shov.unlimstorage.views

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.insets.navigationBarsPadding
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import com.shov.unlimstorage.ui.CustomIconButton
import com.shov.unlimstorage.utils.launchWhenStarted
import com.shov.unlimstorage.utils.observeConnectivityAsFlow
import com.shov.unlimstorage.viewModels.SignInViewModel
import com.shov.unlimstorage.viewModels.TopAppBarViewModel
import com.shov.unlimstorage.viewModels.common.ScaffoldViewModel
import com.shov.unlimstorage.viewModels.provider.singletonViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.onEach

@Composable
fun SignInScreen(
	context: Context = LocalContext.current,
	lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
	scaffoldViewModel: ScaffoldViewModel = singletonViewModel(),
	signInViewModel: SignInViewModel = hiltViewModel(),
	topAppBarViewModel: TopAppBarViewModel = singletonViewModel(),
	onSignIn: () -> Unit,
) {
	val hasConnection by context.observeConnectivityAsFlow().collectAsState(false)

	SignInScreen(
		hasConnection = hasConnection,
		launchedEffect = {
			topAppBarViewModel.setTopBar(title = context.getString(R.string.app_name))

			signInViewModel.serviceAccess.onEach { access ->
				if (access) onSignIn()
			}.launchWhenStarted(lifecycleOwner.lifecycleScope)
		},
		onActivityResult = signInViewModel::checkAccessWithResult,
		onClick = signInViewModel::getAccess,
		onHasNoConnection = {
			scaffoldViewModel.showSnackbar(context.getString(R.string.connection_failed))
		}
	)
}

@Composable
fun SignInScreen(
	hasConnection: Boolean,
	launchedEffect: suspend CoroutineScope.() -> Unit,
	onActivityResult: (result: ActivityResult, storageType: StorageType) -> Unit,
	onClick: (ManagedActivityResultLauncher<Intent, ActivityResult>, StorageType) -> Unit,
	onHasNoConnection: () -> Unit
) {
	Column {
		Spacer(modifier = Modifier.weight(1f))

		Text(
			text = stringResource(id = R.string.sign_in_with),
			modifier = Modifier
				.fillMaxWidth()
				.padding(bottom = 32.dp),
			textAlign = TextAlign.Center
		)

		Row(modifier = Modifier.navigationBarsPadding()) {
			StorageType.values().forEach { storageType ->
				val launcher = rememberLauncherForActivityResult(
					ActivityResultContracts.StartActivityForResult()
				) { result: ActivityResult -> onActivityResult(result, storageType) }

				CustomIconButton(
					painter = painterResource(storageType.imageId),
					text = stringResource(storageType.nameId)
				) {
					if (hasConnection) {
						onClick(launcher, storageType)
					} else {
						onHasNoConnection()
					}
				}
			}
		}
	}

	LaunchedEffect(key1 = null, block = launchedEffect)
}

@Preview
@Composable
fun SignInScreenPreview() {
	SignInScreen(
		hasConnection = true,
		launchedEffect = {},
		onActivityResult = { _, _ -> },
		onClick = { _, _ -> },
		onHasNoConnection = {}
	)
}
