package com.shov.unlimstorage.views.files

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.OpenInBrowser
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.items.ItemType
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import com.shov.unlimstorage.ui.StoreItem
import com.shov.unlimstorage.ui.buttons.CustomIconButton

@Composable
fun FileActionsView(
	disk: StorageType,
	name: String,
	onDoesntWork: () -> Unit,
	size: String?,
	type: ItemType,
	onShowInfo: () -> Unit
) {
	Column {
		StoreItem(
			disk = disk,
			enabled = false,
			isDividerVisible = false,
			isOptionVisible = false,
			name = name,
			size = size,
			type = type
		)

		Divider()

		Row(modifier = Modifier.padding(vertical = 12.dp)) {
			CustomIconButton(
				imageVector = Icons.Rounded.Download,
				text = stringResource(id = R.string.download),
				onClick = onDoesntWork
			)

			CustomIconButton(
				imageVector = Icons.Rounded.OpenInBrowser,
				text = stringResource(id = R.string.open_in_browser),
				onClick = onDoesntWork
			)

			CustomIconButton(
				imageVector = Icons.Rounded.Share,
				text = stringResource(id = R.string.share_link),
				onClick = onDoesntWork
			)

			CustomIconButton(
				imageVector = Icons.Rounded.Info,
				text = stringResource(id = R.string.show_info),
				onClick = onShowInfo
			)
		}

		Divider()
	}
}
