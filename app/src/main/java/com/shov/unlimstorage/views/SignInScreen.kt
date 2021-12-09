package com.shov.unlimstorage.views

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import com.shov.unlimstorage.ui.CustomSnackbarHost
import com.shov.unlimstorage.ui.SignInButton
import com.shov.unlimstorage.utils.launchWhenStarted
import com.shov.unlimstorage.utils.observeConnectivityAsFlow
import com.shov.unlimstorage.values.PADDING_BIG
import com.shov.unlimstorage.viewModels.SignInViewModel
import com.shov.unlimstorage.viewModels.TopAppBarViewModel
import com.shov.unlimstorage.viewModels.provider.singletonViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
	context: Context = LocalContext.current,
	lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
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
		onClick = signInViewModel::getAccess
	)
}

@Composable
fun SignInScreen(
	context: Context = LocalContext.current,
	coroutineScope: CoroutineScope = rememberCoroutineScope(),
	hasConnection: Boolean,
	launchedEffect: suspend CoroutineScope.() -> Unit,
	onActivityResult: (result: ActivityResult, storageType: StorageType) -> Unit,
	onClick: (ManagedActivityResultLauncher<Intent, ActivityResult>, StorageType) -> Unit,
	scaffoldState: ScaffoldState = rememberScaffoldState(),
) {
	Scaffold(
		scaffoldState = scaffoldState,
		snackbarHost = CustomSnackbarHost
	) {
		Column(
			horizontalAlignment = Alignment.CenterHorizontally,
			modifier = Modifier.fillMaxSize()
		) {
			Text(
				color = MaterialTheme.colors.onSurface,
				modifier = Modifier.padding(vertical = PADDING_BIG),
				text = stringResource(id = R.string.sign_in_with)
			)

			StorageType.values().forEach { storageType ->
				val launcher = rememberLauncherForActivityResult(
					ActivityResultContracts.StartActivityForResult()
				) { result: ActivityResult -> onActivityResult(result, storageType) }

				SignInButton(
					storageType = storageType,
					onClick = {
						if (hasConnection) {
							onClick(launcher, storageType)
						} else {
							coroutineScope.launch {
								scaffoldState.snackbarHostState.showSnackbar(
									message = context.getString(R.string.connection_failed)
								)
							}
						}
					}
				)
			}
		}

		LaunchedEffect(key1 = null, block = launchedEffect)
	}
}

@Preview
@Composable
fun SignInScreenPreview() {
	SignInScreen(
		hasConnection = true,
		launchedEffect = {},
		onActivityResult = { _, _ -> },
		onClick = { _, _ -> }
	)
}
