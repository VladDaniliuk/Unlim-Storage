package com.shov.coreui.ui

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shov.coreui.models.TopAppBarStatus
import com.shov.coreui.ui.texts.CustomText

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RowScope.SearchBarTitle(
	focusRequester: FocusRequester,
	status: TopAppBarStatus,
	textSearch: String,
	title: String?,
	onTextChange: (String) -> Unit
) {
	Box(
		modifier = Modifier
			.align(Alignment.CenterVertically)
			.weight(1f)
	) {
		if (status == TopAppBarStatus.Search) {
			TextField(
				value = textSearch,
				onValueChange = onTextChange,
				modifier = Modifier.focusRequester(focusRequester),
				singleLine = true,
				maxLines = 1,
				placeholder = {
					CustomText(text = "Search files and folders")
				},
				colors = TextFieldDefaults.textFieldColors(
					containerColor = Color.Transparent,
					focusedIndicatorColor = Color.Transparent,
					unfocusedIndicatorColor = Color.Transparent
				)
			)
		} else {
			AnimatedContent(
				modifier = Modifier
					.fillMaxWidth()
					.padding(horizontal = 4.dp),
				targetState = title,
				transitionSpec = {
					(slideInVertically { height -> height } + fadeIn()
							with slideOutVertically { height -> -height } + fadeOut())
						.using(SizeTransform(clip = false))
				}
			) { targetText ->
				CustomText(
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					text = targetText,
					textAlign = TextAlign.Center,
					textStyle = Typography().titleLarge,
				)
			}
		}
	}

	LaunchedEffect(key1 = status) {
		if (status == TopAppBarStatus.Search) {
			focusRequester.requestFocus()
		}
	}
}

@Preview
@Composable
fun SearchBarTitlePreview() {
	Row {
		SearchBarTitle(FocusRequester(), TopAppBarStatus.Search, "", "") {}
	}
}
