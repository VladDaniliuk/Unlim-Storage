package com.shov.unlimstorage.ui.texts.animation

import androidx.compose.animation.*
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.shov.unlimstorage.ui.texts.CustomText


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedText(
	modifier: Modifier,
	color: Color = LocalContentColor.current,
	maxLines: Int = 1,
	text: String?,
	textAlign: TextAlign = TextAlign.Center,
	textOverflow: TextOverflow = TextOverflow.Ellipsis,
	textStyle: TextStyle = Typography().h6
) {
	AnimatedContent(
		modifier = modifier,
		targetState = text,
		transitionSpec = {
			(slideInVertically { height -> height } + fadeIn()
					with slideOutVertically { height -> -height } + fadeOut())
				.using(SizeTransform(clip = false))
		}
	) { targetText ->
		CustomText(
			color = color,
			maxLines = maxLines,
			overflow = textOverflow,
			text = targetText,
			textAlign = textAlign,
			textStyle = textStyle,
		)
	}
}
