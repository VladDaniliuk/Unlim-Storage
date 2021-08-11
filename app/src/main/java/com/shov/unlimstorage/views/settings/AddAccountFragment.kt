package com.shov.unlimstorage.views.settings

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.alorma.settings.composables.SettingsMenuLink
import com.google.accompanist.insets.navigationBarsPadding
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.getSignInButtons
import com.shov.unlimstorage.values.ACCOUNTS
import com.shov.unlimstorage.values.PADDING_SMALL_PLUS
import com.shov.unlimstorage.viewModels.SignInViewModel

@ExperimentalMaterialApi
@Composable
fun AddAccountBottomSheet(
	bottomSheetState: ModalBottomSheetState,
	signInViewModel: SignInViewModel
) {

	ModalBottomSheetLayout(
		sheetState = bottomSheetState,
		sheetContent = {
			Text(
				text = stringResource(R.string.choose_drive),
				modifier = Modifier
					.align(Alignment.CenterHorizontally)
					.padding(top = PADDING_SMALL_PLUS)
			)

			getSignInButtons(signInViewModel).forEach { signInButtonInfo ->
				SettingsMenuLink(
					icon = {
						Icon(
							painter = painterResource(id = signInButtonInfo.image),
							contentDescription = ACCOUNTS
						)
					},
					title = {
						Text(
							text = stringResource(
								id = R.string.add_account,
								stringResource(id = signInButtonInfo.buttonId)
							)
						)
					}
				) {}
			}

			Spacer(modifier = Modifier.navigationBarsPadding())
		}) {}
}
