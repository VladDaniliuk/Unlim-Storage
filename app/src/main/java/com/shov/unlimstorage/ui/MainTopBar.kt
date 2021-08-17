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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.shov.unlimstorage.R
import com.shov.unlimstorage.values.BACK
import com.shov.unlimstorage.values.HEADLINE6
import com.shov.unlimstorage.values.PADDING_SMALL
import com.shov.unlimstorage.values.navSettings

data class TopBarObject(
	val navController: NavController? = null,
	val nextRoute: Pair<ImageVector, String>? = null,
	val prevRoute: Boolean = false,
	val text: Int
)

@Composable
fun MainTopBar(topBarObject: TopBarObject) {
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
			if (topBarObject.prevRoute) {
				IconButton(
					modifier = Modifier
						.align(Alignment.CenterStart)
						.padding(start = PADDING_SMALL),
					onClick = { topBarObject.navController?.popBackStack() }) {
					Icon(
						contentDescription = BACK,
						imageVector = Icons.Rounded.ArrowBack
					)
				}
			}

			Text(
				fontSize = HEADLINE6,
				modifier = Modifier.align(Alignment.Center),
				text = stringResource(id = topBarObject.text),
				fontWeight = FontWeight.Medium,
			)

			topBarObject.nextRoute?.let { nextRoute ->
				IconButton(
					modifier = Modifier
						.align(Alignment.CenterEnd)
						.padding(end = PADDING_SMALL),
					onClick = { topBarObject.navController?.navigate(nextRoute.second) }) {
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
		topBarObject = TopBarObject(
			navController = rememberNavController(),
			nextRoute = Pair(Icons.Rounded.AccountCircle, navSettings),
			prevRoute = true,
			text = R.string.app_name
		)
	)
}
