package com.shov.unlimstorage.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.shov.unlimstorage.R
import com.shov.unlimstorage.values.HEADLINE6
import com.shov.unlimstorage.values.PADDING_SMALL
import com.shov.unlimstorage.values.navMain
import com.shov.unlimstorage.values.navSettings

@Composable
fun MainTopBar(
	text: Int,
	navController: NavController,
	prevRoute: Pair<ImageVector, String>?,
	nextRoute: Pair<ImageVector, String>?
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
					onClick = { navController.navigate(prevRoute.second) }) {
					Icon(
						contentDescription = prevRoute.second,
						imageVector = prevRoute.first
					)
				}
			}

			Text(
				fontSize = HEADLINE6,
				modifier = Modifier.align(Alignment.Center),
				text = stringResource(id = text),
			)

			nextRoute?.let { nextRoute ->
				IconButton(
					modifier = Modifier
						.align(Alignment.CenterEnd)
						.padding(end = PADDING_SMALL),
					onClick = { navController.navigate(nextRoute.second) }) {
					Icon(
						contentDescription = nextRoute.second,
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
		navController = rememberNavController(),
		nextRoute = Pair(Icons.Rounded.AccountCircle, navSettings),
		prevRoute = Pair(Icons.Rounded.ArrowBack, navMain),
		text = R.string.app_name
	)
}
