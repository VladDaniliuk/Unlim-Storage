package com.shov.unlimstorage.views.settings.accounts

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.getSignInButtons
import com.shov.unlimstorage.ui.AccountMenuLink
import com.shov.unlimstorage.values.PADDING_SMALL
import com.shov.unlimstorage.values.PADDING_SMALL_PLUS
import com.shov.unlimstorage.viewModels.AccountsViewModel

@ExperimentalMaterialApi
@Composable
fun AddAccountDialog(accountsViewModel: AccountsViewModel) {
	Dialog(onDismissRequest = { accountsViewModel.setShowAddAccountBottomSheet(null) }) {
		Column(
			modifier = Modifier.background(
				color = MaterialTheme.colors.surface,
				shape = MaterialTheme.shapes.medium
			)
		) {
			Text(
				text = stringResource(R.string.choose_drive),
				modifier = Modifier
					.align(Alignment.CenterHorizontally)
					.padding(top = PADDING_SMALL_PLUS)
			)

			getSignInButtons(hiltViewModel()).forEach { signInButtonInfo ->
				if (accountsViewModel.checkAccess(signInType = signInButtonInfo.signInType).not()) {
					val startForResult = rememberLauncherForActivityResult(
						ActivityResultContracts.StartActivityForResult()
					) { result: ActivityResult ->
						signInButtonInfo.checkAccess(result, signInButtonInfo.signInType)
						accountsViewModel.setAllSignedIn(true)
						accountsViewModel.setShowAddAccountBottomSheet(null)
					}

					AccountMenuLink(
						accountId = signInButtonInfo.buttonId,
						imageId = signInButtonInfo.image,
						titleId = R.string.add_account
					) {
						signInButtonInfo.getAccess(startForResult, signInButtonInfo.signInType)
					}
				}
			}

			Spacer(modifier = Modifier.padding(top = PADDING_SMALL))
		}
	}
}

@ExperimentalMaterialApi
@Preview
@Composable
fun AddAccountBottomSheetPreview() {
	AddAccountDialog(
		accountsViewModel = hiltViewModel()
	)
}
