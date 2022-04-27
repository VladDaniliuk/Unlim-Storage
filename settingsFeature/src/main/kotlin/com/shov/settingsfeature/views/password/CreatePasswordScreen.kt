package com.shov.settingsfeature.views.password

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.coreui.viewModels.NavigationViewModel
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.settingsfeature.R
import com.shov.settingsfeature.viewModels.password.CreatePasswordViewModel

@Composable
fun CreatePasswordScreen(
	context: Context = LocalContext.current,
	createPasswordViewModel: CreatePasswordViewModel = hiltViewModel(),
	scaffold: ScaffoldViewModel = singletonViewModel(),
	navigationViewModel: NavigationViewModel = singletonViewModel()
) {
	PasswordScreen(
		modifier = Modifier.windowInsetsPadding(
			WindowInsets.navigationBars.only(WindowInsetsSides.Bottom + WindowInsetsSides.Top)
		),
		onError = {
			scaffold.showSnackbar(context.getString(R.string.password_length_error))
		},
		onRightClick = { pinCode ->
			createPasswordViewModel.onCreatePass(pinCode)
			scaffold.showSnackbar(
				context.getString(R.string.new_, context.getString(R.string.password_is_enabled))
			)

			navigationViewModel.popBack()
		}
	)

	LaunchedEffect(key1 = null) {
		scaffold.setTopBar(
			prevRoute = Icons.Rounded.ArrowBack to navigationViewModel::popBack,
			title = context.getString(R.string.create_password)
		)
	}
}
