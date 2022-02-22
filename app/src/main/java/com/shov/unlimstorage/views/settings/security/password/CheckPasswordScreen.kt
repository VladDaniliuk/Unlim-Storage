package com.shov.unlimstorage.views.settings.security.password

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.navigationBarsPadding
import com.shov.unlimstorage.R
import com.shov.unlimstorage.utils.checkForAuthenticate
import com.shov.unlimstorage.utils.showBiometricAuthentication
import com.shov.unlimstorage.viewModels.common.ScaffoldViewModel
import com.shov.unlimstorage.viewModels.common.TopAppBarViewModel
import com.shov.unlimstorage.viewModels.provider.singletonViewModel
import com.shov.unlimstorage.viewModels.settings.security.password.CheckPasswordViewModel

@Composable
fun CheckPasswordScreen(
	checkPasswordViewModel: CheckPasswordViewModel = hiltViewModel(),
	context: Context = LocalContext.current,
	topAppBarViewModel: TopAppBarViewModel = singletonViewModel(),
	scaffoldViewModel: ScaffoldViewModel = singletonViewModel(),
	onAccess: () -> Unit
) {
	Column(
		modifier = Modifier.navigationBarsPadding(
			start = false,
			end = false
		)
	) {
		PasswordScreen(
			modifier = Modifier.weight(1f),
			onError = {
				scaffoldViewModel.showSnackbar(context.getString(R.string.password_length_error))
			},
			onRightClick = { pinCode ->
				checkPasswordViewModel.checkPass(
					pass = pinCode,
					onAccess = {
						scaffoldViewModel.showSnackbar(context.getString(R.string.passwords_equal))
						onAccess()
					}
				) {
					scaffoldViewModel.showSnackbar(context.getString(R.string.passwords_dont_equal))
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
				onClick = { fragmentActivity.showBiometricAuthentication(onAccess) }
			) {
				Text(text = stringResource(R.string.use_fingerprint))
			}
		}
	}

	LaunchedEffect(key1 = null) {
		topAppBarViewModel.setTopBar(title = context.getString(R.string.check_password))
	}
}