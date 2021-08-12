package com.shov.unlimstorage.views.settings

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.navigationBarsPadding
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.getSignInButtons
import com.shov.unlimstorage.ui.AccountMenuLink
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
				if (signInViewModel.checkAccess(signInType = signInButtonInfo.signInType).not()) {
					AccountMenuLink(
						accountId = signInButtonInfo.buttonId,
						imageId = signInButtonInfo.image,
						titleId = R.string.add_account
					) {}
				}
			}

			Spacer(modifier = Modifier.navigationBarsPadding())
		}) {}
}

@ExperimentalMaterialApi
@Preview
@Composable
fun AddAccountBottomSheetPreview() {
	AddAccountBottomSheet(
		bottomSheetState = rememberModalBottomSheetState(
			initialValue = ModalBottomSheetValue.Expanded
		),
		signInViewModel = hiltViewModel()
	)
}
