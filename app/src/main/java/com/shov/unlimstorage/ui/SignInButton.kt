package com.shov.unlimstorage.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.shov.unlimstorage.models.signInModels.StorageType
import com.shov.unlimstorage.values.ACCOUNTS
import com.shov.unlimstorage.values.PADDING_MEDIUM
import com.shov.unlimstorage.values.PADDING_NULL
import com.shov.unlimstorage.values.PADDING_SMALL_PLUS

@Composable
fun SignInButton(storageType: StorageType, onClick: () -> Unit) {
	Button(
		onClick = onClick,
		modifier = Modifier.padding(PADDING_NULL, PADDING_MEDIUM)
	) {
		Icon(
			painter = painterResource(id = storageType.imageId),
			contentDescription = ACCOUNTS
		)

		Spacer(modifier = Modifier.padding(horizontal = PADDING_SMALL_PLUS))

		Text(text = stringResource(id = storageType.nameId))
	}
}

@Preview
@Composable
fun SignInButtonPreview() {
	SignInButton(storageType = StorageType.GOOGLE) {}
}
