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
import com.shov.settingsfeature.viewModels.password.RemovePasswordViewModel

@Composable
fun RemovePasswordScreen(
	context: Context = LocalContext.current,
	removePasswordViewModel: RemovePasswordViewModel = hiltViewModel(),
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
			removePasswordViewModel.checkPass(
				pass = pinCode,
				onAccess = {
					removePasswordViewModel.removePass()
					scaffold.showSnackbar(context.getString(R.string.password_removed))

					navigationViewModel.popBack()
				},
				onError = {
					scaffold.showSnackbar(context.getString(R.string.passwords_dont_equal))
				}
			)
		}
	)

	LaunchedEffect(key1 = null) {
		scaffold.setTopBar(
			prevRoute = Icons.Rounded.ArrowBack to navigationViewModel::popBack,
			title = context.getString(R.string.check_password_for_remove)
		)
	}
}
