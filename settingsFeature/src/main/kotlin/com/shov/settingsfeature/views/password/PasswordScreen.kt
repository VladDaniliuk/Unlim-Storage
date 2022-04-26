package com.shov.settingsfeature.views.password

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shov.settingsfeature.viewModels.PasswordViewModel

@Composable
fun PasswordScreen(
	modifier: Modifier = Modifier,
	passwordViewModel: PasswordViewModel = viewModel(),
	orientation: Int = LocalConfiguration.current.orientation,
	onError: () -> Unit,
	onRightClick: (String) -> Unit
) = when (orientation) {
	Configuration.ORIENTATION_LANDSCAPE -> Row(modifier = modifier) {
		PasswordView(
			isPassVisible = passwordViewModel.isPassVisible,
			password = passwordViewModel.pass,
			onChangeVisibility = passwordViewModel::changeVisibility,
			onLeftClick = passwordViewModel::removeLastInt,
			isOnLeftClickEnabled = passwordViewModel.pass.isNotEmpty(),
			onLeftLongClick = { passwordViewModel.removeLastInt(true) },
			onRightClick = {
				passwordViewModel.submitPass(
					onError = onError,
					onRightClick = onRightClick
				)
			},
			onClick = passwordViewModel::changePass
		)
	}
	else -> Column(modifier = modifier) {
		PasswordView(
			isPassVisible = passwordViewModel.isPassVisible,
			password = passwordViewModel.pass,
			onChangeVisibility = passwordViewModel::changeVisibility,
			onLeftClick = passwordViewModel::removeLastInt,
			isOnLeftClickEnabled = passwordViewModel.pass.isNotEmpty(),
			onLeftLongClick = { passwordViewModel.removeLastInt(true) },
			onRightClick = {
				passwordViewModel.submitPass(
					onError = onError,
					onRightClick = onRightClick
				)
			},
			onClick = passwordViewModel::changePass
		)
	}
}

@Preview
@Composable
fun PasswordScreenPreview() {
	PasswordScreen(
		onError = {},
		passwordViewModel = hiltViewModel()
	) {}
}
