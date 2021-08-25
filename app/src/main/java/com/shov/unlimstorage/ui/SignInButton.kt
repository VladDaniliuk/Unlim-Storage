package com.shov.unlimstorage.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.shov.unlimstorage.models.SignInButtonInfo
import com.shov.unlimstorage.values.ACCOUNTS
import com.shov.unlimstorage.values.PADDING_MEDIUM
import com.shov.unlimstorage.values.PADDING_NULL
import com.shov.unlimstorage.values.PADDING_SMALL_PLUS

@Composable
fun SignInButton(signInButtonInfo: SignInButtonInfo) {
	val startForResult = rememberLauncherForActivityResult(
		ActivityResultContracts.StartActivityForResult()
	) { result: ActivityResult ->
		signInButtonInfo.checkAccess(result, signInButtonInfo.storageType)
	}

	Button(
		onClick = { signInButtonInfo.getAccess(startForResult, signInButtonInfo.storageType) },
		modifier = Modifier.padding(PADDING_NULL, PADDING_MEDIUM)
	) {
		Icon(
			painter = painterResource(id = signInButtonInfo.image),
			contentDescription = ACCOUNTS
		)

		Spacer(modifier = Modifier.padding(horizontal = PADDING_SMALL_PLUS))

		Text(text = stringResource(id = signInButtonInfo.buttonId))
	}
}
