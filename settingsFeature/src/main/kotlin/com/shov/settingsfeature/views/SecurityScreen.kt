package com.shov.settingsfeature.views

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
import com.shov.coreui.viewModels.NavigationViewModel
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.coreutils.values.Screen
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.settingsfeature.R
import com.shov.settingsfeature.ui.ChangePasswordMenuLink
import com.shov.settingsfeature.ui.EnableBiometricMenuLink
import com.shov.settingsfeature.ui.RemovePasswordMenuLink
import com.shov.settingsfeature.ui.SetPasswordMenuLink
import com.shov.settingsfeature.utils.checkForAuthenticate
import com.shov.settingsfeature.viewModels.SecurityViewModel

@Composable
fun SecurityScreen(
	context: Context = LocalContext.current,
	scaffold: ScaffoldViewModel = singletonViewModel(),
	securityViewModel: SecurityViewModel = hiltViewModel(),
	navigationViewModel: NavigationViewModel = singletonViewModel()
) {
	SecurityView(
		canAuthWithBiometric = BiometricManager.from(context).checkForAuthenticate(),
		isBiometricCheckedState = securityViewModel.isBiometricEnabled,
		isPinCodeSetUp = securityViewModel.isPassSetUp,
		onChangePasswordClick = { navigationViewModel.navigateTo(Screen.ChangePassword.route) },
		onCheckBoxChange = securityViewModel::setBiometricEnable,
		onPasswordSetClick = { navigationViewModel.navigateTo(Screen.CreatePassword.route) },
		onRemovePasswordClick = { navigationViewModel.navigateTo(Screen.RemovePassword.route) },
		onCantAuthWithBiometric = {
			scaffold.showSnackbar(context.getString(R.string.check_biometric_on_settings))
		}
	)

	LaunchedEffect(key1 = null) {
		scaffold.setTopBar(
			prevRoute = Icons.Rounded.ArrowBack to navigationViewModel::popBack,
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
			isBiometricChecked = isBiometricCheckedState.and(canAuthWithBiometric),
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
