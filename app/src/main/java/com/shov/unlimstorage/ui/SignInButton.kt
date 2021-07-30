package com.shov.unlimstorage.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.shov.unlimstorage.models.SignInButtonInfo
import com.shov.unlimstorage.values.PADDING_MEDIUM
import com.shov.unlimstorage.values.PADDING_NULL

@Composable
fun SignInButton(signInButtonInfo: SignInButtonInfo) {
	val startForResult = rememberLauncherForActivityResult(
		ActivityResultContracts.StartActivityForResult()
	) { result: ActivityResult ->
		signInButtonInfo.checkAccess(result, signInButtonInfo.signInType)
	}

	Button(
		onClick = { signInButtonInfo.getAccess(startForResult, signInButtonInfo.signInType) },
		modifier = Modifier.padding(PADDING_NULL, PADDING_MEDIUM)
	) {
		Text(text = stringResource(id = signInButtonInfo.buttonId))
	}
}
