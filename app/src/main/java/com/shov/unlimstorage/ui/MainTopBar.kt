package com.shov.unlimstorage.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.shov.unlimstorage.R
import com.shov.unlimstorage.values.PADDING_SMALL

@Composable
fun MainTopBar(
	prevRoute: Pair<ImageVector, () -> Unit>? = null,
	title: String? = null,
	nextRoute: Pair<ImageVector, () -> Unit>? = null
) {
	TopAppBar(
		contentPadding = rememberInsetsPaddingValues(
			applyBottom = false,
			insets = LocalWindowInsets.current.statusBars
		),
	) {
		Box(
			modifier = Modifier
				.fillMaxSize()
				.padding(all = PADDING_SMALL)
		) {
			prevRoute?.let { prevRoute ->
				IconButton(
					modifier = Modifier.align(Alignment.CenterStart),
					onClick = prevRoute.second
				) {
					Icon(
						contentDescription = prevRoute.first.name,
						imageVector = prevRoute.first
					)
				}
			}

			title?.let { title ->
				Text(
					fontSize = Typography().h6.fontSize,
					fontStyle = Typography().h6.fontStyle,
					fontWeight = Typography().h6.fontWeight,
					maxLines = 1,
					modifier = Modifier
						.padding(horizontal = 48.dp)
						.align(Alignment.Center),
					overflow = TextOverflow.Ellipsis,
					text = title
				)
			}

			nextRoute?.let { nextRoute ->
				IconButton(
					modifier = Modifier.align(Alignment.CenterEnd),
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

@Preview(name = "without buttons | Long name")
@Composable
fun MainTopBarPreview() {
	MainTopBar(title = "Long name Long name Long name")
}

@Preview(name = "backstack button")
@Composable
fun MainTopBarBackPreview() {
	MainTopBar(
		prevRoute = Icons.Rounded.ArrowBack to {},
		title = stringResource(id = R.string.app_name)
	)
}

@Preview(name = "two buttons")
@Composable
fun MainTopBarTwoButtonsPreview() {
	MainTopBar(
		prevRoute = Icons.Rounded.ArrowBack to {},
		title = stringResource(id = R.string.app_name),
		nextRoute = Icons.Rounded.AccountCircle to {}
	)
}
