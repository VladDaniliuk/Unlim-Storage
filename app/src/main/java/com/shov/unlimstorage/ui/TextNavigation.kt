package com.shov.unlimstorage.ui

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.shov.unlimstorage.R
import com.shov.unlimstorage.values.ARRAY_INDEX_EXCEPTION
import com.shov.unlimstorage.values.navMain

@Composable
fun TextNavigation(
	stringIdArray: Array<Int>,
	clickableStringIndex: Int,
	direction: String,
	navController: NavController,
	modifier: Modifier
) {
	if (clickableStringIndex < stringIdArray.size) {
		val annotatedString = buildAnnotatedString {
			for (item in stringIdArray.indices) {
				if (clickableStringIndex == item) {
					pushStringAnnotation(
						tag = direction,
						annotation = direction
					)
					pushStyle(style = SpanStyle(color = MaterialTheme.colors.error))
					append(stringResource(stringIdArray[item]))
					pop()
				} else {
					pushStyle(style = SpanStyle(color = MaterialTheme.colors.onSecondary))
					append(stringResource(stringIdArray[item]))
					pop()
				}
			}

		}

		ClickableText(
			modifier = modifier,
			text = annotatedString,
			onClick = { position ->
				annotatedString.getStringAnnotations(
					tag = direction,
					start = position,
					end = position
				).firstOrNull()?.let {
					navController.navigate(it.item)
				}
			}
		)
	} else throw IllegalArgumentException(ARRAY_INDEX_EXCEPTION)
}

@Preview
@Composable
fun TextNavigationPreview() {
	TextNavigation(
		stringIdArray = arrayOf(R.string.google, R.string.accounts_description),
		clickableStringIndex = 0,
		direction = navMain(),
		navController = rememberNavController(),
		modifier = Modifier
	)
}

