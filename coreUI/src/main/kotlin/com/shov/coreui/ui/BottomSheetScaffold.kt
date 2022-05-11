package com.shov.coreui.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.layout.SubcomposeLayout

@Composable
fun BottomSheetScaffold(
	hostState: SnackbarHostState = SnackbarHostState(),
	content: @Composable () -> Unit
) {
	CompositionLocalProvider(LocalHostState provides hostState) {
		SubcomposeLayout { constraints ->
			val contentPlaceable = subcompose("content", content)[0].measure(constraints)
			val snackbarPlaceables = subcompose("snackbar") {
				CustomSnackbarHost(hostState)
			}[0].measure(constraints)

			layout(contentPlaceable.width, contentPlaceable.height) {
				contentPlaceable.place(0, 0)

				snackbarPlaceables.place(
					(contentPlaceable.width - snackbarPlaceables.width) / 2,
					contentPlaceable.height - snackbarPlaceables.height
				)
			}
		}
	}
}
