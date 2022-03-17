package com.shov.signinfeature.views

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shov.coremodels.models.StorageType
import com.shov.coreui.ui.buttons.CustomIconButton
import com.shov.signinfeature.R

@Composable
fun SignInView(
	onActivityResult: (result: ActivityResult, storageType: StorageType) -> Unit,
	onClick: (StorageType) -> Intent?,
) {
	Column {
		Spacer(modifier = Modifier.weight(1f))

		Text(
			text = stringResource(id = R.string.sign_in_with),
			modifier = Modifier
				.fillMaxWidth()
				.padding(bottom = 32.dp),
			textAlign = TextAlign.Center
		)

		Row(Modifier.navigationBarsPadding()) {
			StorageType.values().forEach { storageType: StorageType ->
				val launcher = rememberLauncherForActivityResult(
					ActivityResultContracts.StartActivityForResult()
				) { result: ActivityResult ->
					onActivityResult(result, storageType)
				}

				CustomIconButton(
					painter = painterResource(storageType.imageId),
					text = stringResource(storageType.nameId),
					onClick = {
						onClick(storageType)?.let(launcher::launch)
					}
				)
			}
		}
	}
}

@Preview
@Composable
fun SignInPreview() {
	SignInView(onActivityResult = { _, _ -> }) { null }
}
