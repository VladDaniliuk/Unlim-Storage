package com.shov.coreui.ui.menuLinks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Switch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shov.coreui.ui.icons.CustomIcon

@Composable
fun MenuLink(
	icon: @Composable BoxScope.() -> Unit,
	title: String,
	subtitle: String? = null,
	action: (@Composable () -> Unit)? = null,
	onClick: () -> Unit
) {
	Row(
		modifier = Modifier
			.height(64.dp)
			.fillMaxWidth()
			.clickable(onClick = onClick),
		verticalAlignment = Alignment.CenterVertically
	) {
		Box(
			modifier = Modifier.size(64.dp),
			contentAlignment = Alignment.Center,
			content = icon
		)

		MenuText(
			title = title,
			subtitle = subtitle
		)

		MenuAction(action = action)
	}
}

@Preview
@Composable
private fun MenuLinkPreview() {
	MenuLink(
		icon = {
			CustomIcon(imageVector = Icons.Default.Face)
		},
		title = "Title",
		subtitle = "Subtitle",
		action = {
			Switch(
				checked = false,
				onCheckedChange = {}
			)
		}
	) {}
}
