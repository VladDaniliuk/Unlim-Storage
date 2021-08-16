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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.getSignInButtons
import com.shov.unlimstorage.ui.SignInButton
import com.shov.unlimstorage.utils.launchWhenStarted
import com.shov.unlimstorage.values.PADDING_BIG
import com.shov.unlimstorage.values.PADDING_NULL
import com.shov.unlimstorage.values.navMain
import com.shov.unlimstorage.viewModels.SignInViewModel
import kotlinx.coroutines.flow.onEach

@Composable
fun SignInFragment(signInViewModel: SignInViewModel, navController: NavController) {
	Column(
		modifier = Modifier.fillMaxWidth(),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(
			text = stringResource(id = R.string.sign_in_with),
			modifier = Modifier.padding(horizontal = PADDING_NULL, vertical = PADDING_BIG),
			color = MaterialTheme.colors.onSurface
		)

		getSignInButtons(signInViewModel).forEach { info -> SignInButton(info) }
	}

	signInViewModel.serviceAccess.onEach { access ->
		if (access) navController.navigate(navMain)
	}.launchWhenStarted(LocalLifecycleOwner.current.lifecycleScope)
}
