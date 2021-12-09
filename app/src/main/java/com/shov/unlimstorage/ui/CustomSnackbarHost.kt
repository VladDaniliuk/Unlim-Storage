package com.shov.unlimstorage.ui

import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.insets.navigationBarsPadding

val CustomSnackbarHost: @Composable (hostState: SnackbarHostState) -> Unit = { hostState ->
	SnackbarHost(
		hostState = hostState,
		modifier = Modifier.navigationBarsPadding()
	) { snackBarData ->
		Snackbar(snackbarData = snackBarData)
	}
}