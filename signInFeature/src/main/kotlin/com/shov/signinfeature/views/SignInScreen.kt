package com.shov.signinfeature.views

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.coreui.ui.LocalHostState
import com.shov.coreui.viewModels.NavigationViewModel
import com.shov.coreui.viewModels.TopAppBarViewModel
import com.shov.coreui.views.CustomScaffold
import com.shov.coreutils.utils.observeConnectivityAsFlow
import com.shov.coreutils.values.Screen
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.signinfeature.R
import com.shov.signinfeature.viewModels.SignInViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.shov.coremodels.R as coreModelsR

@Composable
fun SignInScreen(
	context: Context = LocalContext.current,
	signInViewModel: SignInViewModel = hiltViewModel(),
	coroutine: CoroutineScope = rememberCoroutineScope(),
	topAppBarViewModel: TopAppBarViewModel = singletonViewModel(),
	navigationViewModel: NavigationViewModel = singletonViewModel()
) {
	val hasConnection by context.observeConnectivityAsFlow().collectAsState(false)

	CustomScaffold {
		val snackbar: SnackbarHostState = LocalHostState.current

		SignInView(
			onActivityResult = signInViewModel::checkAccessWithResult,
			onClick = { storageType ->
				if (hasConnection) {
					return@SignInView signInViewModel.getAccess(storageType)
				} else {
					coroutine.launch {
						snackbar.showSnackbar(context.getString(R.string.connection_failed))
					}

					return@SignInView null
				}
			},
		)
	}

	LaunchedEffect(key1 = signInViewModel.serviceAccess) {
		signInViewModel.signIn {
			navigationViewModel.navigateTo(
				destination = Screen.Files.route,
				popUp = Screen.SignIn.route,
				inclusive = true
			)
		}
	}

	LaunchedEffect(key1 = null) {
		topAppBarViewModel.setTopBar(title = context.getString(coreModelsR.string.app_name))
	}
}
