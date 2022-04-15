package com.shov.filesfeature.ui.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.shov.coreui.ui.buttons.AnimatedIconButton
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.coreutils.viewModels.singletonViewModel

@Composable
fun CustomBottomAppBar(scaffold: ScaffoldViewModel = singletonViewModel()) {
	Box(
		modifier = Modifier
			.background(color = MaterialTheme.colorScheme.primary)
			.navigationBarsPadding()
	) {
		BottomAppBar(
			containerColor = MaterialTheme.colorScheme.primary,
			contentColor = MaterialTheme.colorScheme.onPrimary,
		) {
			AnimatedIconButton(
				modifier = Modifier.align(Alignment.CenterVertically),
				enabled = true,
				imageVector = Icons.Rounded.Download,
				onClick = {
					scaffold.showSnackbar("List with downloads unavailable now")
				}
			)
		}
	}
}
