package com.shov.unlimstorage.views.settings.security

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.unlimstorage.R
import com.shov.unlimstorage.utils.checkForAuthenticate
import com.shov.unlimstorage.values.Screen
import com.shov.unlimstorage.viewModels.provider.singletonViewModel
import com.shov.unlimstorage.viewModels.settings.security.SecurityViewModel
import com.shov.unlimstorage.views.settings.security.securityView.ChangePasswordMenuLink
import com.shov.unlimstorage.views.settings.security.securityView.EnableBiometricMenuLink
import com.shov.unlimstorage.views.settings.security.securityView.RemovePasswordMenuLink
import com.shov.unlimstorage.views.settings.security.securityView.SetPasswordMenuLink

@Composable
fun SecurityScreen(
	context: Context = LocalContext.current,
	onNavigate: (String) -> Unit,
	popBackStack: () -> Unit,
	scaffold: ScaffoldViewModel = singletonViewModel(),
	securityViewModel: SecurityViewModel = hiltViewModel()
) {
	SecurityView(
		canAuthWithBiometric = BiometricManager.from(context).checkForAuthenticate(),
		isBiometricCheckedState = securityViewModel.isBiometricEnabled,
		isPinCodeSetUp = securityViewModel.isPassSetUp,
		onChangePasswordClick = { onNavigate(Screen.ChangePassword.route) },
		onCheckBoxChange = securityViewModel::setBiometricEnable,
		onPasswordSetClick = { onNavigate(Screen.CreatePassword.route) },
		onRemovePasswordClick = { onNavigate(Screen.RemovePassword.route) },
		onCantAuthWithBiometric = {
			scaffold.showSnackbar(context.getString(R.string.check_biometric_on_settings))
		}
	)

	LaunchedEffect(key1 = null) {
		scaffold.setTopBar(
			prevRoute = Icons.Rounded.ArrowBack to popBackStack,
			title = context.getString(R.string.security)
		)
	}
}

@Composable
fun SecurityView(
	canAuthWithBiometric: Boolean = true,
	isBiometricCheckedState: Boolean,
	isPinCodeSetUp: Boolean = false,
	onChangePasswordClick: () -> Unit = {},
	onCheckBoxChange: (Boolean) -> Unit = {},
	onPasswordSetClick: () -> Unit = {},
	onRemovePasswordClick: () -> Unit = {},
	onCantAuthWithBiometric: () -> Unit = {}
) = Column {
	if (isPinCodeSetUp) {
		ChangePasswordMenuLink(onChangePasswordClick = onChangePasswordClick)

		RemovePasswordMenuLink(onRemovePasswordClick = onRemovePasswordClick)

		EnableBiometricMenuLink(
			isBiometricChecked = isBiometricCheckedState,
			enabled = canAuthWithBiometric,
			onCheckBoxChange = onCheckBoxChange,
			onClick = {
				if (canAuthWithBiometric) onCheckBoxChange(isBiometricCheckedState.not())
				else onCantAuthWithBiometric()
			}
		)
	} else {
		SetPasswordMenuLink(onPasswordSetClick = onPasswordSetClick)
	}
}

@Preview(name = "Pin is enabled")
@Composable
fun SecurityScreenPreview() {
	SecurityView(
		isPinCodeSetUp = true,
		isBiometricCheckedState = true
	)
}

@Preview(name = "Pin is not enabled")
@Composable
fun SecurityScreenPreview2() {
	SecurityView(isBiometricCheckedState = false)
}
