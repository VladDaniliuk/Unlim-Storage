package com.shov.filesfeature.views.fileInfo.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shov.coremodels.models.ItemType
import com.shov.coremodels.models.StorageType
import com.shov.coremodels.models.UserItem
import com.shov.coreui.ui.texts.CustomText
import com.shov.filesfeature.R
import com.shov.filesfeature.ui.button.MaxWidthButton

@Composable
fun FileInfoView(
	id: String?,
	itemType: ItemType?,
	storageTypeImageId: Int?,
	fileSize: String?,
	createdTime: String?,
	modifiedTime: String?,
	version: String?,
	onDescriptionClick: () -> Unit,
	description: String?,
	sharingUsers: List<UserItem>?,
	link: String?,
	onShareLink: () -> Unit,
	onDownloadClick: () -> Unit,
	onShowSnackbar: (String) -> Unit,
	onRenameClick: () -> Unit,
) {
	Column(
		modifier = Modifier
			.verticalScroll(state = rememberScrollState())
			.navigationBarsPadding()
	) {
		FileAdditionalInfoView(
			id = id,
			mainIcon = itemType?.imageVector,
			secondaryIconId = storageTypeImageId,
			fileSize = fileSize,
			createdTime = createdTime,
			modifiedTime = modifiedTime,
			version = version
		)

		FileDescriptionView(
			description = description,
			onIconClick = onDescriptionClick
		)

		FileUsersView(
			sharingUsers = sharingUsers,
			onShowSnackbar = onShowSnackbar
		)

		FileLinkView(
			link = link,
			onShowSnackbar = onShowSnackbar,
			onShareLink = onShareLink
		)

		MaxWidthButton(
			text = stringResource(id = R.string.download),
			onClick = onDownloadClick
		)

		Button(
			modifier = Modifier.padding(
				horizontal = 8.dp,
				vertical = 4.dp
			),
			onClick = onRenameClick
		) {
			CustomText(text = stringResource(id = R.string.rename))
		}
	}
}

@Preview
@Composable
fun FileInfoPreview() {
	FileInfoView(
		id = "id",
		itemType = ItemType.FILE,
		storageTypeImageId = StorageType.GOOGLE.imageId,
		fileSize = "12 Mb",
		createdTime = "12:00:00 January 12 2009",
		modifiedTime = "12:00:00 January 12 2009",
		version = "12",
		onDescriptionClick = {},
		description = "Description",
		sharingUsers = List(10) {
			UserItem(
				email = "email1",
				role = "Role1",
				name = "Name1"
			)
		},
		link = "123",
		onShareLink = {},
		onDownloadClick = {},
		onShowSnackbar = {},
	) {}
}
