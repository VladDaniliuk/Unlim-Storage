package com.shov.unlimstorage.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.shov.unlimstorage.R
import com.shov.unlimstorage.values.PADDING_SMALL

@Composable
fun MainTopBar(
	prevRoute: Pair<ImageVector, () -> Unit>? = null,
	textId: Int? = null,
	nextRoute: Pair<ImageVector, () -> Unit>? = null
) {
	TopAppBar(
		contentPadding = rememberInsetsPaddingValues(
			applyBottom = false,
			insets = LocalWindowInsets.current.statusBars
		),
		modifier = Modifier.fillMaxWidth(),
	) {
		Box(
			modifier = Modifier
				.fillMaxHeight()
				.fillMaxWidth()
		) {
			prevRoute?.let { prevRoute ->
				IconButton(
					modifier = Modifier
						.align(Alignment.CenterStart)
						.padding(start = PADDING_SMALL),
					onClick = prevRoute.second
				) {
					Icon(
						contentDescription = prevRoute.first.name,
						imageVector = prevRoute.first
					)
				}
			}

			textId?.let { textId ->
				Text(
					fontSize = Typography().h6.fontSize,
					fontStyle = Typography().h6.fontStyle,
					fontWeight = Typography().h6.fontWeight,
					modifier = Modifier.align(Alignment.Center),
					text = stringResource(id = textId)
				)
			}

			nextRoute?.let { nextRoute ->
				IconButton(
					modifier = Modifier
						.align(Alignment.CenterEnd)
						.padding(end = PADDING_SMALL),
					onClick = nextRoute.second
				) {
					Icon(
						contentDescription = nextRoute.first.name,
						imageVector = nextRoute.first
					)
				}
			}
		}
	}
}

@Preview
@Composable
fun MainTopBarPreview() {
	MainTopBar(
		prevRoute = Icons.Rounded.ArrowBack to {},
		textId = R.string.app_name,
		nextRoute = Icons.Rounded.AccountCircle to {}
	)
}
