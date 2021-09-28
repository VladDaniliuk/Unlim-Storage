package com.shov.unlimstorage.ui

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import com.shov.unlimstorage.R

@Composable
fun TextNavigation(
	stringIdArray: Array<Int>,
	taggedStringId: Int,
	modifier: Modifier = Modifier,
	onClick: () -> Unit
) {
	val annotatedString = buildAnnotatedString {
		stringIdArray.forEach { stringId ->
			if (taggedStringId == stringId) {
				pushStringAnnotation(
					tag = taggedStringId.toString(),
					annotation = taggedStringId.toString()
				)
				pushStyle(style = SpanStyle(color = MaterialTheme.colors.error))
			} else {
				pushStyle(style = SpanStyle(color = MaterialTheme.colors.onSecondary))
			}

			append(stringResource(id = stringId))
			pop()
		}
	}

	ClickableText(
		modifier = modifier,
		text = annotatedString,
		onClick = { position ->
			annotatedString.getStringAnnotations(
				tag = taggedStringId.toString(),
				start = position,
				end = position
			).firstOrNull()?.let {
				onClick.invoke()
			}
		}
	)
}

@Preview
@Composable
fun TextNavigationPreview() {
	TextNavigation(
		stringIdArray = arrayOf(
			R.string.accounts_description,
			R.string.google,
			R.string.accounts_description
		), taggedStringId = R.string.google
	) {}
}

