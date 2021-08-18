package com.shov.unlimstorage.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
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
import androidx.compose.ui.unit.dp
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.ItemType
import com.shov.unlimstorage.models.StoreItem
import com.shov.unlimstorage.models.signInModels.StorageType
import com.shov.unlimstorage.values.BODY2
import com.shov.unlimstorage.values.DRIVE
import com.shov.unlimstorage.values.HEADLINE5
import com.shov.unlimstorage.values.PADDING_SMALL


@Composable
fun StoreItem(storeItem: StoreItem, onClick: () -> Unit) {
	Row(
		modifier = Modifier
			.clickable(
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
				.width(48.dp)
				.height(48.dp)
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
				fontSize = HEADLINE5
			)

			Text(
				text = storeItem.size ?: "",
				fontSize = BODY2
			)

			Row(modifier = Modifier.padding(vertical = PADDING_SMALL)) {
				storeItem.disk.forEach { storageType ->
					StoreIconItem(
						storageType = storageType,
						modifier = Modifier.align(Alignment.CenterVertically)
					)

					Spacer(modifier = Modifier.padding(horizontal = PADDING_SMALL))
				}
			}
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
				StorageType.ONEDRIVE -> R.drawable.ic_one_drive
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
			disk = arrayOf(StorageType.BOX, StorageType.GOOGLE)
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
			disk = arrayOf(StorageType.ONEDRIVE, StorageType.DROPBOX)
		),
		onClick = {}
	)
}
