package com.shov.unlimstorage.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.ItemType
import com.shov.unlimstorage.models.StoreItem
import com.shov.unlimstorage.models.signInModels.StorageType
import com.shov.unlimstorage.values.DRIVE
import com.shov.unlimstorage.values.PADDING_SMALL
import com.shov.unlimstorage.values.SIZE_ICON


@Composable
fun StoreItem(storeItem: StoreItem, enabled: Boolean = true, onClick: () -> Unit) {
	Row(
		modifier = Modifier
			.clickable(
				enabled = enabled,
				onClick = if (storeItem.type == ItemType.FOLDER) {
					onClick
				} else {
					{}
				}
			)
			.fillMaxWidth()
	) {
		Icon(
			modifier = Modifier
				.align(Alignment.CenterVertically)
				.size(SIZE_ICON)
				.padding(horizontal = PADDING_SMALL),
			imageVector = when (storeItem.type) {
				ItemType.FOLDER -> Icons.Rounded.Folder
				ItemType.FILE -> Icons.Rounded.Description
			},
			contentDescription = storeItem.type.name
		)

		Column {
			Text(
				text = storeItem.name,
				fontSize = MaterialTheme.typography.h5.fontSize,
				fontStyle = MaterialTheme.typography.h5.fontStyle,
				fontWeight = MaterialTheme.typography.h5.fontWeight
			)

			Text(
				text = storeItem.size ?: "",
				fontSize = MaterialTheme.typography.body2.fontSize,
				fontStyle = MaterialTheme.typography.body2.fontStyle,
				fontWeight = MaterialTheme.typography.body2.fontWeight
			)

			Spacer(modifier = Modifier.padding(vertical = PADDING_SMALL))

			StoreIconItem(
				storageType = storeItem.disk,
				modifier = Modifier
			)

			Spacer(modifier = Modifier.padding(vertical = PADDING_SMALL))

			Divider()
		}
	}
}

@Composable
fun StoreIconItem(storageType: StorageType, modifier: Modifier) {
	Icon(
		modifier = modifier,
		painter = painterResource(
			id = when (storageType) {
				StorageType.BOX -> R.drawable.ic_box
				StorageType.DROPBOX -> R.drawable.ic_drop_box
				StorageType.GOOGLE -> R.drawable.ic_google_drive
			}
		),
		contentDescription = DRIVE,
		tint = Color.Unspecified
	)
}

@Preview
@Composable
fun FilePreview() {
	StoreItem(
		storeItem = StoreItem(
			id = "000000",
			type = ItemType.FILE,
			name = "File preview",
			size = "1210 MB",
			parentFolder = null,
			disk = StorageType.GOOGLE
		),
		onClick = {}
	)
}

@Preview
@Composable
fun FolderPreview() {
	StoreItem(
		storeItem = StoreItem(
			id = "000001",
			type = ItemType.FOLDER,
			name = "Folder preview",
			size = null,
			parentFolder = null,
			disk = StorageType.BOX
		),
		onClick = {}
	)
}
