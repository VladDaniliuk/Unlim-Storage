package com.shov.filesfeature

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
import com.shov.coremodels.models.ItemType
import com.shov.coremodels.models.StorageType
import com.shov.coreui.ui.texts.CustomText

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StoreItemView(
	modifier: Modifier = Modifier,
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
		modifier = modifier
			.combinedClickable(
				onClick = onClick,
				onLongClick = onLongClick,
				enabled = enabled
			)
	) {
		Icon(
			modifier = Modifier
				.padding(horizontal = 4.dp)
				.align(Alignment.CenterVertically)
				.size(48.dp),
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
							.padding(vertical = 4.dp)
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
	StoreItemView(
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
	StoreItemView(
		disk = StorageType.BOX,
		name = "Folder preview",
		size = "1210 MB",
		type = ItemType.FOLDER
	)
}
