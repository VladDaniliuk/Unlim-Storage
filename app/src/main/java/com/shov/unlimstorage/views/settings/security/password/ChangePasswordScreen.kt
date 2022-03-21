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
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.settingsfeature.viewModels.ChangePasswordViewModel
import com.shov.unlimstorage.R

@Composable
fun ChangePasswordScreen(
	context: Context = LocalContext.current,
	changePasswordViewModel: ChangePasswordViewModel = hiltViewModel(),
	scaffold: ScaffoldViewModel = singletonViewModel(),
	popBackStack: () -> Unit
) {
	PasswordScreen(
		modifier = Modifier.navigationBarsPadding(
			start = false,
			end = false
		),
		onError = {
			scaffold.showSnackbar(context.getString(R.string.password_length_error))
		}
	) { pass ->
		changePasswordViewModel.onRightClick(pass, popBackStack) {
			scaffold.showSnackbar(context.getString(R.string.passwords_dont_equal))
		}
	}

	LaunchedEffect(key1 = changePasswordViewModel.isPassChecked) {
		scaffold.setTopBar(
			prevRoute = Icons.Rounded.ArrowBack to popBackStack,
			title = context.getString(changePasswordViewModel.titleId)
		)
	}
}
