package com.shov.settingsfeature.ui

import androidx.compose.material.Switch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChangeCircle
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Fingerprint
import androidx.compose.material.icons.rounded.Password
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.shov.coreui.ui.icons.CustomIcon
import com.shov.coreui.ui.menuLinks.MenuLink
import com.shov.settingsfeature.R

@Composable
fun ChangePasswordMenuLink(onChangePasswordClick: () -> Unit) {
	MenuLink(
		icon = {
			CustomIcon(imageVector = Icons.Rounded.ChangeCircle)
		},
		title = stringResource(R.string.change_pin_code),
		subtitle = stringResource(R.string.use_for_change_pin_code),
		onClick = onChangePasswordClick
	)
}

@Composable
fun EnableBiometricMenuLink(
	isBiometricChecked: Boolean,
	enabled: Boolean,
	onCheckBoxChange: (Boolean) -> Unit,
	onClick: () -> Unit
) {
	MenuLink(
		icon = {
			CustomIcon(imageVector = Icons.Rounded.Fingerprint)
		},
		title = stringResource(R.string.use_biometric),
		subtitle = stringResource(R.string.biometric),
		action = {
			Switch(
				checked = isBiometricChecked,
				enabled = enabled,
				onCheckedChange = onCheckBoxChange
			)
		},
		onClick = onClick
	)
}

@Composable
fun RemovePasswordMenuLink(onRemovePasswordClick: () -> Unit) {
	MenuLink(
		icon = {
			CustomIcon(imageVector = Icons.Rounded.Delete)
		},
		title = stringResource(R.string.remove_pin_code),
		subtitle = stringResource(R.string.use_for_disable_pin_code),
		onClick = onRemovePasswordClick
	)
}

@Composable
fun SetPasswordMenuLink(onPasswordSetClick: () -> Unit) {
	MenuLink(
		icon = {
			CustomIcon(imageVector = Icons.Rounded.Password)
		},
		title = stringResource(R.string.set_pin_code),
		subtitle = stringResource(R.string.use_for_secure),
		onClick = onPasswordSetClick
	)
}
