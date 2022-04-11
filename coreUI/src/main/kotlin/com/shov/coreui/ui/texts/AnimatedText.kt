package com.shov.coreui.ui.texts

import androidx.compose.animation.*
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Typography
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedText(
	modifier: Modifier = Modifier,
	color: Color = LocalContentColor.current,
	maxLines: Int = 1,
	text: String?,
	textAlign: TextAlign = TextAlign.Center,
	textOverflow: TextOverflow = TextOverflow.Ellipsis,
	textStyle: TextStyle = Typography().titleLarge
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

@Preview
@Composable
private fun AnimatedTextPreview() {
	val coroutine: CoroutineScope = rememberCoroutineScope()
	var text by remember { mutableStateOf("Text0") }

	AnimatedText(modifier = Modifier, text = text)

	LaunchedEffect(key1 = null) {
		coroutine.launch {
			delay(1000)

			text = "Text1"
		}
	}
}
