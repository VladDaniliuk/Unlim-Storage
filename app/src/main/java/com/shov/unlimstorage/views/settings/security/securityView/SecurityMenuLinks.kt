package com.shov.unlimstorage.views.settings.security.securityView

import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChangeCircle
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Fingerprint
import androidx.compose.material.icons.rounded.Password
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.shov.unlimstorage.R

@Composable
fun ChangePasswordMenuLink(onChangePasswordClick: () -> Unit) = SettingsMenuLink(
	icon = {
		Icon(
			imageVector = Icons.Rounded.ChangeCircle,
			contentDescription = Icons.Rounded.ChangeCircle.name
		)
	},
	subtitle = { Text(text = stringResource(R.string.use_for_change_pin_code)) },
	title = { Text(text = stringResource(R.string.change_pin_code)) },
	onClick = onChangePasswordClick
)

@Composable
fun EnableBiometricMenuLink(
	isBiometricChecked: Boolean,
	enabled: Boolean,
	onCheckBoxChange: (Boolean) -> Unit,
	onClick: () -> Unit
) = SettingsMenuLink(
	icon = {
		Icon(
			imageVector = Icons.Rounded.Fingerprint,
			contentDescription = Icons.Rounded.Fingerprint.name
		)
	},
	subtitle = { Text(text = stringResource(R.string.use_biometric)) },
	title = { Text(text = stringResource(R.string.biometric)) },
	action = {
		Switch(
			checked = isBiometricChecked,
			enabled = enabled,
			onCheckedChange = onCheckBoxChange
		)
	},
	onClick = onClick
)

@Composable
fun RemovePasswordMenuLink(onRemovePasswordClick: () -> Unit) = SettingsMenuLink(
	icon = {
		Icon(
			imageVector = Icons.Rounded.Delete,
			contentDescription = Icons.Rounded.Delete.name
		)
	},
	subtitle = { Text(text = stringResource(R.string.use_for_disable_pin_code)) },
	title = { Text(text = stringResource(R.string.remove_pin_code)) },
	onClick = onRemovePasswordClick
)

@Composable
fun SetPasswordMenuLink(onPasswordSetClick: () -> Unit) = SettingsMenuLink(
	icon = {
		Icon(
			imageVector = Icons.Rounded.Password,
			contentDescription = Icons.Rounded.Password.name
		)
	},
	subtitle = { Text(text = stringResource(R.string.use_for_secure)) },
	title = { Text(text = stringResource(R.string.set_pin_code)) },
	onClick = onPasswordSetClick
)
