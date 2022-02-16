package com.shov.unlimstorage.views.settings.security.password

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.navigationBarsPadding
import com.shov.unlimstorage.R
import com.shov.unlimstorage.viewModels.common.ScaffoldViewModel
import com.shov.unlimstorage.viewModels.common.TopAppBarViewModel
import com.shov.unlimstorage.viewModels.provider.singletonViewModel
import com.shov.unlimstorage.viewModels.settings.security.password.CreatePasswordViewModel

@Composable
fun CreatePasswordScreen(
	context: Context = LocalContext.current,
	createPasswordViewModel: CreatePasswordViewModel = hiltViewModel(),
	popBackStack: () -> Unit,
	topAppBarViewModel: TopAppBarViewModel = singletonViewModel(),
	scaffoldViewModel: ScaffoldViewModel = singletonViewModel()
) {
	PasswordScreen(
		modifier = Modifier.navigationBarsPadding(
			start = false,
			end = false
		),
		onError = {
			scaffoldViewModel.showSnackbar(context.getString(R.string.password_length_error))
		},
		onRightClick = { pinCode ->
			createPasswordViewModel.onCreatePass(pinCode)
			scaffoldViewModel.showSnackbar(
				context.getString(R.string.new_, context.getString(R.string.password_is_enabled))
			)
			popBackStack()
		}
	)

	LaunchedEffect(key1 = null) {
		topAppBarViewModel.setTopBar(
			prevRoute = Icons.Rounded.ArrowBack to popBackStack,
			title = context.getString(R.string.create_password)
		)
	}
}
