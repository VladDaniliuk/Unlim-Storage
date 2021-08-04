package com.shov.unlimstorage.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.getSignInButtons
import com.shov.unlimstorage.ui.SignInButton
import com.shov.unlimstorage.values.CustomTheme
import com.shov.unlimstorage.values.PADDING_BIG
import com.shov.unlimstorage.values.PADDING_NULL
import com.shov.unlimstorage.values.navMain
import com.shov.unlimstorage.viewModels.SignInViewModel

@Composable
fun SignInFragment(signInViewModel: SignInViewModel, navController: NavController) {
	CustomTheme {
		Column(
			modifier = Modifier.fillMaxWidth(),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Text(
				text = stringResource(id = R.string.sign_in_with),
				modifier = Modifier.padding(PADDING_NULL, PADDING_BIG),
				color = MaterialTheme.colors.onSurface
			)

			getSignInButtons(signInViewModel).forEach { info -> SignInButton(info) }
		}
	}

	signInViewModel.serviceAccess.observe(LocalLifecycleOwner.current) { access ->
		if (access) navController.navigate(navMain)
	}
}
