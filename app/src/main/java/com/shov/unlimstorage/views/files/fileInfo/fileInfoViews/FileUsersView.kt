package com.shov.unlimstorage.views.files.fileInfo.fileInfoViews

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.items.User
import com.shov.unlimstorage.ui.UserInfo
import com.shov.unlimstorage.ui.texts.CustomText

@Composable
fun FileUsersView(sharingUsers: List<User>?, onShowSnackbar: (String) -> Unit) {
	val context = LocalContext.current
	val hapticFeedback = LocalHapticFeedback.current
	val clipboardManager = LocalClipboardManager.current

	sharingUsers?.let { users ->
		Column {
			CustomText(
				modifier = Modifier.padding(horizontal = 16.dp),
				text = stringResource(R.string.sharing_access),
				textStyle = Typography().subtitle2
			)

			LazyRow(
				modifier = Modifier
					.fillMaxWidth()
					.padding(vertical = 8.dp)
			) {
				items(items = users) { user ->
					UserInfo(
						modifier = Modifier.padding(start = 8.dp),
						onClick = {
							onShowSnackbar(context.getString(R.string.user_click_message))
						},
						onLongClick = {
							hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)

							if (user.email.isNullOrEmpty()) {
								onShowSnackbar(context.getString(R.string.dont_have_email))
							} else {
								clipboardManager.setText(AnnotatedString(user.email))

								onShowSnackbar(context.getString(R.string.user_long_click_message))
							}
						},
						iconLink = user.photoLink,
						iconSize = 32.dp,
						title = user.name ?: stringResource(R.string.user),
						subtitle = user.role
					)
				}
			}
		}

		Divider()
	}
}

@Preview
@Composable
fun FileUsersPreview() {
	FileUsersView(
		sharingUsers = List(size = 9) {
			User(
				email = "email1",
				role = "Role1",
				name = "Name1"
			)
		}
	) {}
}
