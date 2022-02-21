package com.shov.unlimstorage.views.signIn

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.repositories.signIn.CheckDropboxCredential
import com.shov.unlimstorage.utils.context.observeConnectivityAsFlow
import com.shov.unlimstorage.viewModels.SignInViewModel
import com.shov.unlimstorage.viewModels.common.ScaffoldViewModel
import com.shov.unlimstorage.viewModels.common.TopAppBarViewModel
import com.shov.unlimstorage.viewModels.provider.singletonViewModel

@Composable
fun SignInScreen(
	context: Context = LocalContext.current,
	scaffoldViewModel: ScaffoldViewModel = singletonViewModel(),
	signInViewModel: SignInViewModel = hiltViewModel(),
	topAppBarViewModel: TopAppBarViewModel = singletonViewModel(),
	onSignIn: () -> Unit,
) {
	val hasConnection by context.observeConnectivityAsFlow().collectAsState(false)

	SignInView(
		onActivityResult = signInViewModel::checkAccessWithResult,
		onClick = { launcher, storageType ->
			if (hasConnection) {
				signInViewModel.getAccess(launcher, storageType)
			} else {
				scaffoldViewModel.showSnackbar(context.getString(R.string.connection_failed))
			}
		},
	)

	LaunchedEffect(key1 = signInViewModel.serviceAccess) {
		signInViewModel.singIn(onSignIn)
	}

	LaunchedEffect(key1 = null) {
		topAppBarViewModel.setTopBar(title = context.getString(R.string.app_name))
	}

	CheckDropboxCredential(onGetCredential = signInViewModel::onDropBoxSignIn)
}
