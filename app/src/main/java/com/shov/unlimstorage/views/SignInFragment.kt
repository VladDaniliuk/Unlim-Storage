package com.shov.unlimstorage.views

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.google.accompanist.insets.navigationBarsPadding
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.signInModels.StorageType
import com.shov.unlimstorage.ui.SignInButton
import com.shov.unlimstorage.utils.launchWhenStarted
import com.shov.unlimstorage.utils.observeConnectivityAsFlow
import com.shov.unlimstorage.values.PADDING_BIG
import com.shov.unlimstorage.values.navMain
import com.shov.unlimstorage.values.navSignIn
import com.shov.unlimstorage.viewModels.SignInViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@Composable
fun SignInFragment(signInViewModel: SignInViewModel, navController: NavController) {
	val coroutineScope = rememberCoroutineScope()
	val scaffoldState = rememberScaffoldState()

	val currentLifecycleOwner = LocalLifecycleOwner.current

	val hasConnection by LocalContext.current.observeConnectivityAsFlow()
		.collectAsState(initial = false)

	Scaffold(
		scaffoldState = scaffoldState,
		snackbarHost = { hostState ->
			SnackbarHost(
				hostState = hostState,
				modifier = Modifier.navigationBarsPadding()
			) { data ->
				Snackbar(snackbarData = data)
			}
		}
	) {
		val messageFailed = stringResource(id = R.string.connection_failed)

		Column(
			modifier = Modifier.fillMaxWidth(),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Text(
				text = stringResource(id = R.string.sign_in_with),
				modifier = Modifier.padding(vertical = PADDING_BIG),
				color = MaterialTheme.colors.onSurface
			)

			StorageType.values().forEach { storageType ->
				val launcher = rememberLauncherForActivityResult(
					ActivityResultContracts.StartActivityForResult()
				) { result: ActivityResult ->
					signInViewModel.checkAccessWithResult(result, storageType)
				}

				SignInButton(storageType = storageType) {
					if (hasConnection) {
						signInViewModel.getAccess(launcher, storageType)
					} else {
						coroutineScope.launch {
							scaffoldState.snackbarHostState.showSnackbar(message = messageFailed)
						}
					}
				}
			}
		}
	}

	LaunchedEffect(key1 = null) {
		signInViewModel.serviceAccess.onEach { access ->
			if (access) navController.navigate(navMain()) {
				popUpTo(navSignIn) {
					inclusive = true
				}
			}
		}.launchWhenStarted(currentLifecycleOwner.lifecycleScope)
	}
}

@Preview(showSystemUi = true)
@Composable
fun SignInFragmentPreview() {
	Box(
		modifier = Modifier
			.fillMaxSize()
			.verticalScroll(rememberScrollState())
	) {
		Column(
			modifier = Modifier.fillMaxWidth(),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Text(
				text = stringResource(id = R.string.sign_in_with),
				modifier = Modifier.padding(vertical = PADDING_BIG),
				color = MaterialTheme.colors.onSurface
			)

			StorageType.values().forEach { storageType ->
				SignInButton(storageType = storageType) {}
			}
		}

		Snackbar(
			modifier = Modifier
				.padding(12.dp)
				.align(Alignment.BottomCenter),
			content = { Text(stringResource(id = R.string.connection_failed)) },
		)
	}
}
