package com.shov.unlimstorage.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shov.unlimstorage.models.ItemType
import com.shov.unlimstorage.models.StoreItem
import com.shov.unlimstorage.models.signInModels.StorageType
import com.shov.unlimstorage.values.PADDING_SMALL
import com.shov.unlimstorage.values.SIZE_ICON


@ExperimentalFoundationApi
@Composable
fun StoreItem(
	storeItem: StoreItem,
	enabled: Boolean = true,
	isDividerVisible: Boolean = true,
	isOptionVisible: Boolean = true,
	onClick: () -> Unit = {},
	onLongClick: () -> Unit = {},
	onOptionClick: () -> Unit = {}
) {
	Row(
		modifier = Modifier
			.combinedClickable(
				onClick = onClick,
				onLongClick = onLongClick,
				enabled = enabled
			)
	) {
		Icon(
			modifier = Modifier
				.padding(horizontal = PADDING_SMALL)
				.align(Alignment.CenterVertically)
				.size(SIZE_ICON),
			imageVector = storeItem.type.imageVector,
			contentDescription = storeItem.type.name
		)

		Column {
			Row {
				Column(modifier = Modifier.weight(1f)) {
					Text(
						overflow = TextOverflow.Ellipsis,
						maxLines = 1,
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

					Icon(
						painter = painterResource(id = storeItem.disk.imageId),
						contentDescription = storeItem.disk.name,
						modifier = Modifier
							.padding(vertical = PADDING_SMALL)
							.height(24.dp),
						tint = Color.Unspecified
					)
				}

				if (isOptionVisible) {
					IconButton(
						modifier = Modifier.align(Alignment.CenterVertically),
						onClick = onOptionClick
					) {
						Icon(
							imageVector = Icons.Rounded.MoreVert,
							contentDescription = Icons.Rounded.MoreVert.name
						)
					}
				}
			}

			if (isDividerVisible) {
				Divider()
			}
		}
	}
}

@ExperimentalFoundationApi
@Preview(name = "File with long name")
@Composable
fun FilePreview() {
	StoreItem(
		storeItem = StoreItem(
			id = "000000",
			type = ItemType.FILE,
			name = "Test test test test test test test test test",
			size = "1210 MB",
			parentFolder = null,
			disk = StorageType.GOOGLE
		)
	)
}

@ExperimentalFoundationApi
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
		)
	)
}
