package com.shov.settingsfeature.views.password

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.coreui.viewModels.NavigationViewModel
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.coreutils.values.Screen
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.settingsfeature.R
import com.shov.settingsfeature.utils.checkForAuthenticate
import com.shov.settingsfeature.utils.showBiometricAuthentication
import com.shov.settingsfeature.viewModels.password.CheckPasswordViewModel

@Composable
fun CheckPasswordScreen(
	checkPasswordViewModel: CheckPasswordViewModel = hiltViewModel(),
	context: Context = LocalContext.current,
	scaffold: ScaffoldViewModel = singletonViewModel(),
	navigationViewModel: NavigationViewModel = singletonViewModel(),
) {
	Column(
		modifier = Modifier.windowInsetsPadding(
			WindowInsets.navigationBars.only(WindowInsetsSides.Bottom + WindowInsetsSides.Top)
		),
	) {
		PasswordScreen(
			modifier = Modifier.weight(1f),
			onError = {
				scaffold.showSnackbar(context.getString(R.string.password_length_error))
			},
			onRightClick = { pinCode ->
				checkPasswordViewModel.checkPass(
					pass = pinCode,
					onAccess = {
						scaffold.showSnackbar(context.getString(R.string.passwords_equal))

						navigationViewModel.navigateTo(
							destination = Screen.Files.route,
							popUp = Screen.CheckPassword.route,
							inclusive = true
						)
					}
				) {
					scaffold.showSnackbar(context.getString(R.string.passwords_dont_equal))
				}
			}
		)

		if (checkPasswordViewModel.isBiometricEnabled and BiometricManager.from(context)
				.checkForAuthenticate()
		) {
			val fragmentActivity = LocalContext.current as FragmentActivity

			Button(
				modifier = Modifier
					.fillMaxWidth()
					.padding(4.dp),
				shape = CircleShape,
				onClick = {
					fragmentActivity.showBiometricAuthentication {
						navigationViewModel.navigateTo(
							destination = Screen.Files.route,
							popUp = Screen.CheckPassword.route,
							inclusive = true
						)
					}
				}
			) {
				Text(text = stringResource(R.string.use_fingerprint))
			}
		}
	}

	LaunchedEffect(key1 = null) {
		scaffold.setTopBar(title = context.getString(R.string.check_password))
	}
}
