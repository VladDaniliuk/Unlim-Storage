package com.shov.unlimstorage.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.insets.navigationBarsPadding
import com.shov.unlimstorage.viewModels.common.ScaffoldViewModel
import com.shov.unlimstorage.viewModels.provider.singletonViewModel

@Composable
fun CustomScaffold(
	scaffoldViewModel: ScaffoldViewModel = singletonViewModel(),
	content: @Composable (PaddingValues) -> Unit
) {
	Scaffold(
		scaffoldState = scaffoldViewModel.scaffoldState,
		snackbarHost = { hostState ->
			SnackbarHost(
				hostState = hostState,
				modifier = Modifier.navigationBarsPadding(
					start = false,
					end = false
				)
			) { snackBarData ->
				Snackbar(snackbarData = snackBarData)
			}
		},
		topBar = { MainTopBar() },
		content = content
	)
}

@Composable
fun FABScaffold(onClick: () -> Unit, content: @Composable (PaddingValues) -> Unit) {
	Scaffold(
		floatingActionButton = {
			FloatingActionButton(
				modifier = Modifier.navigationBarsPadding(
					start = false,
					end = false
				),
				onClick = onClick
			) {
				Icon(
					imageVector = Icons.Rounded.Add,
					contentDescription = Icons.Rounded.Add.name,
					tint = Color.White
				)
			}
		},
		content = content
	)
}
