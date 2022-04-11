package com.shov.settingsfeature.views

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Backspace
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shov.settingsfeature.ui.Keyboard
import com.shov.settingsfeature.ui.PasswordField

@Composable
fun RowScope.PasswordView(
	isOnLeftClickEnabled: Boolean,
	isPassVisible: Boolean,
	onChangeVisibility: () -> Unit,
	onClick: (Int) -> Unit,
	onLeftClick: () -> Unit,
	onLeftLongClick: () -> Unit,
	onRightClick: () -> Unit,
	password: String
) {
	PasswordField(
		modifier = Modifier
			.weight(1f)
			.fillMaxHeight(),
		passIsVisible = isPassVisible,
		password = password,
		onIconClick = onChangeVisibility
	)

	Keyboard(
		modifier = Modifier.weight(1f),
		onLeftClick = onLeftClick,
		onLeftLongClick = onLeftLongClick,
		onLeftClickContent = {
			Icon(
				contentDescription = Icons.Rounded.Backspace.name,
				imageVector = Icons.Rounded.Backspace
			)
		},
		onLeftClickEnabled = isOnLeftClickEnabled,
		onRightClick = onRightClick,
		onRightClickContent = {
			Icon(
				contentDescription = Icons.Rounded.ArrowForward.name,
				imageVector = Icons.Rounded.ArrowForward
			)
		},
		onClick = onClick
	)
}

@Composable
fun ColumnScope.PasswordView(
	isOnLeftClickEnabled: Boolean,
	isPassVisible: Boolean,
	onChangeVisibility: () -> Unit,
	onClick: (Int) -> Unit,
	onLeftClick: () -> Unit,
	onLeftLongClick: () -> Unit,
	onRightClick: () -> Unit,
	password: String
) {
	PasswordField(
		modifier = Modifier
			.weight(1f)
			.fillMaxWidth(),
		passIsVisible = isPassVisible,
		password = password,
		onIconClick = onChangeVisibility
	)

	Keyboard(
		modifier = Modifier.weight(1f),
		onLeftClick = onLeftClick,
		onLeftLongClick = onLeftLongClick,
		onLeftClickContent = {
			Icon(
				contentDescription = Icons.Rounded.Backspace.name,
				imageVector = Icons.Rounded.Backspace
			)
		},
		onLeftClickEnabled = isOnLeftClickEnabled,
		onRightClick = onRightClick,
		onRightClickContent = {
			Icon(
				contentDescription = Icons.Rounded.ArrowForward.name,
				imageVector = Icons.Rounded.ArrowForward
			)
		},
		onClick = onClick
	)
}
