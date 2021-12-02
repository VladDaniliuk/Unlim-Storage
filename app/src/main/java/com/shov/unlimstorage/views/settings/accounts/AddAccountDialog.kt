package com.shov.unlimstorage.views.settings.accounts

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.dropbox.core.android.Auth
import com.dropbox.core.json.JsonReadException
import com.dropbox.core.oauth.DbxCredential
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.preferences.Preference
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import com.shov.unlimstorage.ui.AccountMenuLink
import com.shov.unlimstorage.values.DROPBOX_CREDENTIAL
import com.shov.unlimstorage.values.MEDIUM_SHAPES
import com.shov.unlimstorage.values.PADDING_SMALL_PLUS
import com.shov.unlimstorage.viewModels.SignInViewModel
import com.shov.unlimstorage.viewModels.settings.AccountsViewModel

@Composable
fun AddAccountDialog(
	accountsViewModel: AccountsViewModel = hiltViewModel(),
	signInViewModel: SignInViewModel = hiltViewModel(),
	context: Context = LocalContext.current,
	lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {
	Dialog(onDismissRequest = { accountsViewModel.showAddAccountBottomSheet() }) {
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

			StorageType.values().forEach { storageType ->
				if (accountsViewModel.checkAccess(storageType).not()) {
					val startForResult = rememberLauncherForActivityResult(
						ActivityResultContracts.StartActivityForResult()
					) { result ->
						signInViewModel.checkAccessWithResult(result, storageType)
						accountsViewModel.setIsAllSignedIn(true)
						accountsViewModel.showAddAccountBottomSheet()
					}

					AccountMenuLink(
						accountId = storageType.nameId,
						imageId = storageType.imageId,
						titleId = R.string.add_account
					) {
						signInViewModel.getAccess(startForResult, storageType)
					}
				}
			}

			Spacer(modifier = Modifier.padding(bottom = MEDIUM_SHAPES))
		}
	}
}

@Preview
@Composable
fun AddAccountBottomSheetPreview() {
	AddAccountDialog()
}
