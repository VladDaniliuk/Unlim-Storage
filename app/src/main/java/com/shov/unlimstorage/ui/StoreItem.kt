package com.shov.unlimstorage.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Typography
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
import com.shov.unlimstorage.models.items.ItemType
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import com.shov.unlimstorage.values.PADDING_SMALL
import com.shov.unlimstorage.values.SIZE_ICON_MEDIUM

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StoreItem(
	name: String,
	type: ItemType,
	size: String?,
	disk: StorageType,
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
				.size(SIZE_ICON_MEDIUM),
			imageVector = type.imageVector,
			contentDescription = type.name
		)

		Column {
			Row {
				Column(modifier = Modifier.weight(1f)) {
					CustomText(
						overflow = TextOverflow.Ellipsis,
						maxLines = 1,
						text = name,
						textStyle = Typography().h5
					)

					CustomText(
						text = size,
						textStyle = Typography().body2
					)

					Icon(
						painter = painterResource(id = disk.imageId),
						contentDescription = disk.name,
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
		disk = StorageType.GOOGLE,
		name = "Test test test test test test test test test",
		size = "1210 MB",
		type = ItemType.FILE
	)
}

@ExperimentalFoundationApi
@Preview
@Composable
fun FolderPreview() {
	StoreItem(
		disk = StorageType.BOX,
		name = "Folder preview",
		size = "1210 MB",
		type = ItemType.FOLDER
	)
}
