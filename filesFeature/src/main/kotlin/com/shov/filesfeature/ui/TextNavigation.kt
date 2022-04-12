package com.shov.filesfeature.ui

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import com.shov.filesfeature.R

@Composable
fun TextNavigation(
	modifier: Modifier = Modifier,
	stringIdArray: Array<Int>,
	taggedStringId: Int,
	textColor: Color = LocalContentColor.current,
	onClick: () -> Unit
) {
	val annotatedString = buildAnnotatedString {
		stringIdArray.forEach { stringId ->
			if (taggedStringId == stringId) {
				pushStringAnnotation(
					tag = taggedStringId.toString(),
					annotation = taggedStringId.toString()
				)
				pushStyle(style = SpanStyle(color = MaterialTheme.colorScheme.error))
			} else {
				pushStyle(style = SpanStyle(color = textColor))
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
private fun TextNavigationPreview() {
	TextNavigation(
		stringIdArray = arrayOf(
			R.string.nothing_to_show,
			R.string.settings,
			R.string.dot
		), taggedStringId = R.string.dot
	) {}
}

