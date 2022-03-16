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
import com.shov.unlimstorage.viewModels.provider.singletonViewModel
import com.shov.unlimstorage.viewModels.settings.security.password.RemovePasswordViewModel

@Composable
fun RemovePasswordScreen(
	context: Context = LocalContext.current,
	popBackStack: () -> Unit,
	removePasswordViewModel: RemovePasswordViewModel = hiltViewModel(),
	scaffold: ScaffoldViewModel = singletonViewModel()
) {
	PasswordScreen(
		modifier = Modifier.navigationBarsPadding(
			start = false,
			end = false
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
					popBackStack()
				},
				onError = {
					scaffold.showSnackbar(context.getString(R.string.passwords_dont_equal))
				}
			)
		}
	)

	LaunchedEffect(key1 = null) {
		scaffold.setTopBar(
			prevRoute = Icons.Rounded.ArrowBack to popBackStack,
			title = context.getString(R.string.check_password_for_remove)
		)
	}
}
