package com.shov.autoupdatefeature.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Autorenew
import androidx.compose.material.icons.rounded.Update
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.shov.autoupdatefeature.R
import com.shov.coreui.ui.icons.CustomIcon
import com.shov.coreui.ui.menuLinks.MenuLink

@Composable
internal fun UpdateView(
	onCheckForUpdateClick: () -> Unit,
	isShowAgain: Boolean,
	isShowProgress: Boolean,
	onAutoCheckClick: () -> Unit,
	paddingValues: PaddingValues
) {
	if (isShowProgress) {
		LinearProgressIndicator(
			modifier = Modifier
				.padding(paddingValues)
				.fillMaxWidth(),
			trackColor = Color.Transparent
		)
	}

	LazyColumn(
		modifier = Modifier.fillMaxHeight(),
		contentPadding = paddingValues
	) {
		item {
			MenuLink(
				icon = {
					CustomIcon(imageVector = Icons.Rounded.Update)
				},
				title = stringResource(R.string.check_for_updates),
				onClick = onCheckForUpdateClick
			)

			MenuLink(
				icon = {
					CustomIcon(imageVector = Icons.Rounded.Autorenew)
				},
				title = stringResource(R.string.auto_check),
				action = {
					Switch(
						checked = isShowAgain,
						onCheckedChange = { onAutoCheckClick() }
					)
				},
				onClick = onAutoCheckClick
			)
		}
	}
}

@Preview
@Composable
private fun UpdatePreview() {
	UpdateView(
		onCheckForUpdateClick = {},
		isShowAgain = false,
		isShowProgress = false,
		onAutoCheckClick = {},
		paddingValues = PaddingValues()
	)
}
