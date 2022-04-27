package com.shov.signinfeature.views

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.coremodels.models.StorageType
import com.shov.coreui.ui.buttons.CustomIconButton
import com.shov.coreui.ui.texts.CustomText
import com.shov.coreui.viewModels.NavigationViewModel
import com.shov.coreutils.values.Screen
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.signinfeature.R
import com.shov.signinfeature.viewModels.AddAccountViewModel

@Composable
fun AddAccountDialog(
	addAccountViewModel: AddAccountViewModel = hiltViewModel(),
	navigationViewModel: NavigationViewModel = singletonViewModel(),
) {
	AddAccountView(
		storageTypes = addAccountViewModel.storageTypes,
		onResult = { result: ActivityResult, storageType: StorageType ->
			addAccountViewModel.checkAccessWithResult(result, storageType)

			navigationViewModel.navigateTo(
				destination = Screen.Accounts.route,
				popUp = Screen.Accounts.route,
				inclusive = true
			)
		},
		intent = addAccountViewModel::getAccess
	)
}

@Composable
internal fun AddAccountView(
	storageTypes: List<StorageType>,
	onResult: (result: ActivityResult, storageType: StorageType) -> Unit,
	intent: (storageType: StorageType) -> Intent
) {
	Surface(shape = MaterialTheme.shapes.medium) {
		Column(modifier = Modifier.padding(all = 24.dp)) {
			CustomText(
				modifier = Modifier
					.padding(bottom = 16.dp)
					.fillMaxWidth(),
				text = stringResource(R.string.choose_drive),
				textAlign = TextAlign.Center,
				textStyle = Typography().headlineSmall
			)

			Row {
				storageTypes.forEach { storageType ->
					val startForResult = rememberLauncherForActivityResult(
						ActivityResultContracts.StartActivityForResult()
					) { result ->
						onResult(result, storageType)
					}

					CustomIconButton(
						painter = painterResource(storageType.imageId),
						text = stringResource(storageType.nameId)
					) {
						startForResult.launch(intent(storageType))
					}
				}
			}
		}
	}
}

@Preview
@Composable
private fun AddAccountPreview() {
	AddAccountView(
		storageTypes = StorageType.values().toList(),
		onResult = { _, _ -> },
		intent = { Intent() }
	)
}
