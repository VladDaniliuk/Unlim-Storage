package com.shov.filesfeature.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shov.coreui.ui.texts.CustomText
import com.shov.filesfeature.ui.icon.LoadIcon

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserInfo(
	modifier: Modifier = Modifier,
	onClick: () -> Unit = {},
	onLongClick: () -> Unit = {},
	contentDescription: String = "",
	iconLink: String? = null,
	iconSize: Dp = 32.dp,
	title: String = "",
	subtitle: String = ""
) {
	Column(
		modifier = modifier
			.clip(MaterialTheme.shapes.medium)
			.combinedClickable(
				onClick = onClick,
				onLongClick = onLongClick
			)
			.padding(all = 4.dp)
	) {
		LoadIcon(
			modifier = Modifier
				.align(Alignment.CenterHorizontally)
				.size(iconSize)
				.clip(CircleShape),
			contentDescription = contentDescription,
			defaultTint = MaterialTheme.colorScheme.onBackground,
			iconLink = iconLink
		)

		CustomText(
			modifier = Modifier.align(Alignment.CenterHorizontally),
			text = title,
			textStyle = Typography().titleMedium
		)

		CustomText(
			modifier = Modifier.align(Alignment.CenterHorizontally),
			text = subtitle,
			textStyle = Typography().titleMedium
		)
	}
}
