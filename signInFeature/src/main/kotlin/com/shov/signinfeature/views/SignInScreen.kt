package com.shov.signinfeature.views

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.coreui.viewModels.NavigationViewModel
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.coreui.viewModels.TopAppBarViewModel
import com.shov.coreutils.utils.observeConnectivityAsFlow
import com.shov.coreutils.values.Screen
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.signinfeature.R
import com.shov.signinfeature.viewModels.SignInViewModel
import com.shov.coremodels.R as coreModelsR

@Composable
fun SignInScreen(
	context: Context = LocalContext.current,
	signInViewModel: SignInViewModel = hiltViewModel(),
	scaffold: ScaffoldViewModel = singletonViewModel(),
	topAppBarViewModel: TopAppBarViewModel = singletonViewModel(),
	navigationViewModel: NavigationViewModel = singletonViewModel()
) {
	val hasConnection by context.observeConnectivityAsFlow().collectAsState(false)

	SignInView(
		onActivityResult = signInViewModel::checkAccessWithResult,
		onClick = { storageType ->
			if (hasConnection) {
				return@SignInView signInViewModel.getAccess(storageType)
			} else {
				scaffold.showSnackbar(context.getString(R.string.connection_failed))

				return@SignInView null
			}
		},
	)

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
