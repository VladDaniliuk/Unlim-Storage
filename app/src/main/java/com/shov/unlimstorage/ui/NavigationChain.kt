package com.shov.unlimstorage.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.NavigateNext
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shov.unlimstorage.models.items.BackStack
import com.shov.unlimstorage.ui.icons.IconButton
import com.shov.coreui.ui.buttons.TextButton
import com.shov.unlimstorage.ui.themes.customTheme.CustomTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationChain(
	iconEnabled: Boolean,
	iconOnClick: () -> Unit,
	backStacks: List<BackStack>,
	textOnClick: (index: Int) -> Unit
) = Surface(
	modifier = Modifier
		.fillMaxWidth()
		.horizontalScroll(state = rememberScrollState()),
	color = MaterialTheme.colors.primary
) {
	Row(modifier = Modifier.padding(vertical = 4.dp)) {
		IconButton(
			modifier = Modifier
				.align(Alignment.CenterVertically)
				.padding(start = 4.dp),
			enabled = iconEnabled,
			imageVector = Icons.Rounded.Home,
			ripplePadding = 6.dp,
			onClick = iconOnClick
		)

		backStacks.forEachIndexed { index, backStack ->
			Icon(
				modifier = Modifier.align(Alignment.CenterVertically),
				contentDescription = Icons.Rounded.NavigateNext.name,
				imageVector = Icons.Rounded.NavigateNext
			)

			TextButton(
				modifier = Modifier.align(Alignment.CenterVertically),
				text = backStack.folderName,
				enabled = backStacks.lastIndex != index,
				indicationPadding = 8.dp
			) { textOnClick(index) }
		}
	}

	Spacer(modifier = Modifier.padding(end = 8.dp))
}

@Preview
@Composable
private fun NavigationChainPreview() {
	CustomTheme {
		NavigationChain(
			iconEnabled = true,
			iconOnClick = {},
			backStacks = listOf(BackStack("1", "1", "1"), BackStack("1", "1", "1"))
		) {}
	}
}

@Preview
@Composable
private fun NavigationChainDarkPreview() {
	CustomTheme(darkTheme = true) {
		NavigationChain(
			iconEnabled = true,
			iconOnClick = {},
			backStacks = listOf(BackStack("1", "1", "1"), BackStack("1", "1", "1"))
		) {}
	}
}
