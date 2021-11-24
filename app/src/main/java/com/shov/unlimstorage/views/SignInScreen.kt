package com.shov.unlimstorage.views

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
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
fun SignInScreen(
	scaffoldState: ScaffoldState,
	setTopBar: (
		prevRoute: Pair<ImageVector, (() -> Unit)>?,
		title: String?,
		nextRoute: Pair<ImageVector, (() -> Unit)>?
	) -> Unit,
	signInViewModel: SignInViewModel,
	navController: NavController
) {
	setTopBar(null, stringResource(R.string.app_name), null)

	val hasConnection by LocalContext.current.observeConnectivityAsFlow()
		.collectAsState(initial = false)
	val coroutineScope = rememberCoroutineScope()
	val currentLifecycleOwner = LocalLifecycleOwner.current

	val messageFailed = stringResource(id = R.string.connection_failed)

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

	LaunchedEffect(key1 = null) {
		signInViewModel.serviceAccess.onEach { access ->
			if (access) navController.navigate(navMain) {
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
		modifier = Modifier.fillMaxSize()
	) {
		Column(
			horizontalAlignment = Alignment.CenterHorizontally,
			modifier = Modifier.fillMaxWidth()
		) {
			Text(
				color = MaterialTheme.colors.onSurface,
				modifier = Modifier.padding(vertical = PADDING_BIG),
				text = stringResource(id = R.string.sign_in_with)
			)

			StorageType.values().forEach { storageType ->
				SignInButton(storageType = storageType) {}
			}
		}

		Snackbar(
			content = { Text(stringResource(id = R.string.connection_failed)) },
			modifier = Modifier
				.align(Alignment.BottomCenter)
				.padding(12.dp),
		)
	}
}
