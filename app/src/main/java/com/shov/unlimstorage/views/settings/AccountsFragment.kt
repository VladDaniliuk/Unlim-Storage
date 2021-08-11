package com.shov.unlimstorage.views.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.alorma.settings.composables.SettingsMenuLink
import com.shov.unlimstorage.R
import com.shov.unlimstorage.values.ACCOUNTS
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun AccountsFragment() {
	val bottomSheetState =
		rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
	val scope = rememberCoroutineScope()

	Column {
		Divider()

		SettingsMenuLink(
			icon = {
				Icon(
					imageVector = Icons.Rounded.Add,
					contentDescription = ACCOUNTS
				)
			},
			title = { Text(text = stringResource(R.string.add_other_account)) }
		) {
			scope.launch { bottomSheetState.show() }
		}
	}

	AddAccountBottomSheet(bottomSheetState, hiltViewModel())
}

@Preview
@ExperimentalMaterialApi
@Composable
fun AccountFragmentPreview() {
	AccountsFragment()
}
