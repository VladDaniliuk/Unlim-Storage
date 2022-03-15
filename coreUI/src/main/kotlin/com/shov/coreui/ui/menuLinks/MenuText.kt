package com.shov.coreui.ui.menuLinks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shov.coreui.ui.texts.CustomText

@Composable
internal fun RowScope.MenuText(
	title: String,
	subtitle: String?
) {
	Column(
		modifier = Modifier.weight(1f),
		verticalArrangement = Arrangement.Center
	) {
		CustomText(
			text = title,
			textStyle = MaterialTheme.typography.subtitle1
		)

		if (subtitle != null) {
			CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
				CustomText(
					modifier = Modifier.padding(top = 2.dp),
					text = subtitle,
					textStyle = MaterialTheme.typography.caption
				)
			}
		}
	}
}