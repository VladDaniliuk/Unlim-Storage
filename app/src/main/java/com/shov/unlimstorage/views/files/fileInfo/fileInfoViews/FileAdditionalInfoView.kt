package com.shov.unlimstorage.views.files.fileInfo.fileInfoViews

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Face
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.shov.unlimstorage.R
import com.shov.unlimstorage.ui.icons.ItemTypeIcon
import com.shov.unlimstorage.ui.texts.TextInfo

@Composable
fun FileAdditionalInfoView(
	id: String?,
	mainIcon: ImageVector?,
	secondaryIconId: Int?,
	fileSize: String?,
	createdTime: String?,
	modifiedTime: String?,
	version: String?,
) {
	Column {
		ItemTypeIcon(
			modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
			mainIcon = mainIcon,
			secondaryIcon = secondaryIconId?.let { painterResource(it) },
			secondaryAlignment = Alignment.BottomEnd
		)

		TextInfo(
			name = stringResource(R.string.size_description),
			value = fileSize
		)

		TextInfo(
			name = stringResource(R.string.id_description),
			value = id
		)

		TextInfo(
			name = stringResource(R.string.created_time),
			value = createdTime
		)

		TextInfo(
			name = stringResource(R.string.modified_time),
			value = modifiedTime
		)

		TextInfo(
			name = stringResource(R.string.version_description),
			value = version
		)

		Divider()
	}
}

@Preview
@Composable
fun FileAdditionalInfoPreview() {
	FileAdditionalInfoView(
		id = "123456",
		mainIcon = Icons.Rounded.Face,
		secondaryIconId = R.drawable.ic_google_drive,
		fileSize = "12 MB",
		createdTime = "12:00:00 January 12, 2020",
		modifiedTime = "12:00:00 January 12, 2020",
		version = "1.0.0"
	)
}
