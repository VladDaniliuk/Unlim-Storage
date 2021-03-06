package com.shov.filesfeature.views.fileInfo.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Typography
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
import com.shov.coremodels.models.UserItem
import com.shov.coreui.ui.texts.CustomText
import com.shov.filesfeature.R
import com.shov.filesfeature.ui.UserInfo

@Composable
fun FileUsersView(sharingUsers: List<UserItem>?, onShowSnackbar: (String) -> Unit) {
	val context = LocalContext.current
	val hapticFeedback = LocalHapticFeedback.current
	val clipboardManager = LocalClipboardManager.current

	sharingUsers?.let { users ->
		Column {
			CustomText(
				modifier = Modifier.padding(horizontal = 16.dp),
				text = stringResource(R.string.sharing_access),
				textStyle = Typography().titleSmall
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

							user.email?.let { email ->
								clipboardManager.setText(AnnotatedString(email))

								onShowSnackbar(context.getString(R.string.user_long_click_message))
							} ?: onShowSnackbar(context.getString(R.string.dont_have_email))
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
			UserItem(
				email = "email1",
				role = "Role1",
				name = "Name1"
			)
		}
	) {}
}
