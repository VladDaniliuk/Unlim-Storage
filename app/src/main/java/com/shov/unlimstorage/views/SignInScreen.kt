package com.shov.unlimstorage.views

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import com.shov.unlimstorage.ui.SignInButton
import com.shov.unlimstorage.utils.launchWhenStarted
import com.shov.unlimstorage.utils.observeConnectivityAsFlow
import com.shov.unlimstorage.values.PADDING_BIG
import com.shov.unlimstorage.values.Screen
import com.shov.unlimstorage.viewModels.SignInViewModel
import com.shov.unlimstorage.viewModels.common.TopAppBarViewModel
import com.shov.unlimstorage.viewModels.provider.singletonViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
	context: Context = LocalContext.current,
	coroutineScope: CoroutineScope = rememberCoroutineScope(),
	lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
	navController: NavController,
	scaffoldState: ScaffoldState,
	signInViewModel: SignInViewModel = hiltViewModel(),
	topAppBarViewModel: TopAppBarViewModel = singletonViewModel(),
) {
	val hasConnection by context.observeConnectivityAsFlow().collectAsState(false)

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
						scaffoldState.snackbarHostState.showSnackbar(
							message = context.getString(R.string.connection_failed)
						)
					}
				}
			}
		}
	}

	LaunchedEffect(key1 = null) {
		topAppBarViewModel.setTopBar(title = context.getString(R.string.app_name))

		signInViewModel.serviceAccess.onEach { access ->
			if (access) navController.navigate(Screen.Files.route) {
				popUpTo(Screen.SignIn.route) {
					inclusive = true
				}
			}
		}.launchWhenStarted(lifecycleOwner.lifecycleScope)
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
