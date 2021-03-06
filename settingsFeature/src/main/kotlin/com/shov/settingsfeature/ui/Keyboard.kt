package com.shov.settingsfeature.ui

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Keyboard(
	modifier: Modifier = Modifier,
	onLeftClick: (() -> Unit)? = null,
	onLeftLongClick: (() -> Unit)? = null,
	onLeftClickEnabled: Boolean = (onLeftClick != null) or (onLeftLongClick != null),
	onLeftClickContent: (@Composable BoxScope.() -> Unit)? = null,
	onRightClick: (() -> Unit)? = null,
	onRightLongClick: (() -> Unit)? = null,
	onRightClickEnabled: Boolean = (onRightClick != null) or (onRightLongClick != null),
	onRightClickContent: (@Composable BoxScope.() -> Unit)? = null,
	onClick: (Int) -> Unit,
) = Column(modifier = modifier) {
	for (i in 0..2) {
		Row(modifier = Modifier.weight(1f)) {
			for (j in i * 3 + 1..i * 3 + 3) {
				KeyboardButton(
					modifier = modifier.fillMaxHeight(),
					int = j,
					onClick = { onClick(j) }
				)
			}
		}
	}

	Row(modifier = Modifier.weight(1f)) {
		KeyboardActionButton(
			modifier = modifier.fillMaxHeight(),
			enabled = onLeftClickEnabled,
			contentColor = MaterialTheme.colorScheme.primary,
			onClick = onLeftClick,
			onLongClick = onLeftLongClick,
			content = onLeftClickContent
		)

		KeyboardButton(
			modifier = modifier.fillMaxHeight(),
			int = 0,
			onClick = { onClick(0) }
		)

		KeyboardActionButton(
			modifier = modifier.fillMaxHeight(),
			enabled = onRightClickEnabled,
			contentColor = MaterialTheme.colorScheme.primary,
			onClick = onRightClick,
			onLongClick = onRightLongClick,
			content = onRightClickContent
		)
	}
}

@Preview(widthDp = 540, heightDp = 960)
@Composable
fun KeyboardVerticalPreview() {
	Keyboard(
		onClick = {},
		onRightClick = {},
		onRightClickContent = {
			Icon(imageVector = Icons.Rounded.Download, contentDescription = "")
		}
	)
}

@Preview(heightDp = 540, widthDp = 960)
@Composable
fun KeyboardHorizontalPreview() {
	Keyboard(
		onClick = {},
		onRightClick = {},
		onRightClickContent = {
			Icon(imageVector = Icons.Rounded.Download, contentDescription = "")
		}
	)
}
