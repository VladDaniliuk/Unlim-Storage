package com.shov.filesfeature.views.newObject.newFolder

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shov.coreui.ui.texts.CustomText
import com.shov.filesfeature.R
import com.shov.filesfeature.ui.button.ProgressButton

@Composable
fun NewFolderView(
	focusRequester: FocusRequester,
	value: String,
	onValueChange: (String) -> Unit,
	onTrailingIconClick: () -> Unit,
	onClick: (onError: () -> Unit) -> Unit,
	textError: String
) {
	Column(modifier = Modifier.padding(all = 8.dp)) {
		CustomText(
			modifier = Modifier
				.fillMaxWidth()
				.padding(bottom = 8.dp),
			text = stringResource(R.string.create_new_folder),
			textAlign = TextAlign.Center,
			textStyle = Typography().titleLarge
		)

		Row {
			OutlinedTextField(
				isError = textError.isNotEmpty(),
				modifier = Modifier
					.padding(end = 8.dp)
					.weight(1f)
					.align(Alignment.CenterVertically)
					.focusRequester(focusRequester),
				value = value,
				onValueChange = onValueChange,
				placeholder = { Text(text = stringResource(R.string.folder_name)) },
				trailingIcon = {
					if (value.isNotEmpty()) {
						IconButton(onClick = onTrailingIconClick) {
							Icon(
								imageVector = Icons.Rounded.Close,
								contentDescription = Icons.Rounded.Close.name
							)
						}
					}
				},
				singleLine = true
			)

			ProgressButton(
				modifier = Modifier.align(Alignment.CenterVertically),
				onClick = onClick
			)
		}

		if (textError.isNotEmpty()) Text(
			text = textError,
			color = MaterialTheme.colorScheme.error,
			style = Typography().bodySmall,
		)
	}

	LaunchedEffect(key1 = null) {
		focusRequester.requestFocus()
	}
}

@Preview
@Composable
fun NewFolderPreview() {
	NewFolderView(
		focusRequester = FocusRequester(),
		value = "Text",
		onValueChange = {},
		onTrailingIconClick = {},
		onClick = {},
		textError = "Error"
	)
}
