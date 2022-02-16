package com.shov.unlimstorage.views.files.fileInfo.fileInfoViews

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.navigationBarsPadding
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.items.ItemType
import com.shov.unlimstorage.models.items.User
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import com.shov.unlimstorage.ui.buttons.MaxWidthButton

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
	sharingUsers: List<User>?,
	link: String?,
	onShareLink: () -> Unit,
	onDownloadClick: () -> Unit,
	onShowSnackbar: (String) -> Unit
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
			User(
				email = "email1",
				role = "Role1",
				name = "Name1"
			)
		},
		link = "123",
		onShareLink = {},
		onDownloadClick = {}
	) {}
}