package com.shov.unlimstorage.views.signIn

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.unlimstorage.R
import com.shov.unlimstorage.viewModels.SignInViewModel
import com.shov.unlimstorage.viewModels.common.ScaffoldViewModel
import com.shov.coreui.viewModels.singletonViewModel
import com.shov.unlimstorage.utils.context.observeConnectivityAsFlow

@Composable
fun SignInScreen(
	context: Context = LocalContext.current,
	signInViewModel: SignInViewModel = hiltViewModel(),
	scaffold: ScaffoldViewModel = singletonViewModel(),
	onSignIn: () -> Unit,
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
		signInViewModel.signIn(onSignIn)
	}

	LaunchedEffect(key1 = null) {
		scaffold.setTopBar(title = context.getString(R.string.app_name))
	}
}
