package com.shov.coreui.ui

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier

@Composable
fun CustomSnackbarHost(hostState: SnackbarHostState) = SnackbarHost(
	modifier = Modifier.navigationBarsPadding(),
	hostState = hostState
)

val LocalHostState = compositionLocalOf<SnackbarHostState> { error("SnackbarHostState") }
