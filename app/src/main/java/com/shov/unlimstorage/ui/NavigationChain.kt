package com.shov.unlimstorage.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.NavigateNext
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shov.coreui.ui.buttons.TextButton
import com.shov.coreui.ui.icons.CustomIcon
import com.shov.coreutils.models.BackStack
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
	Row(
		modifier = Modifier.padding(vertical = 4.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		Box(
			modifier = Modifier
				.align(Alignment.CenterVertically)
				.padding(start = 4.dp)
				.clip(CircleShape)
				.clickable(
					onClick = iconOnClick,
					enabled = iconEnabled,
					interactionSource = remember { MutableInteractionSource() },
					indication = rememberRipple(bounded = false)
				)
				.padding(all = 4.dp),
			contentAlignment = Alignment.Center
		) {
			CustomIcon(imageVector = Icons.Rounded.Home)
		}

		backStacks.forEachIndexed { index, backStack ->
			CustomIcon(imageVector = Icons.Rounded.NavigateNext)

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
