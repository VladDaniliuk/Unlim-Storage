package com.shov.unlimstorage.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.shov.unlimstorage.R
import com.shov.unlimstorage.values.HEADLINE6
import com.shov.unlimstorage.values.PADDING_MEDIUM
import com.shov.unlimstorage.values.PADDING_SMALL_MEDIUM
import com.shov.unlimstorage.values.navSettings

@Composable
fun MainTopBar(
	text: Int,
	navController: NavController,
	imageVector: ImageVector
) {
	TopAppBar(
		modifier = Modifier.fillMaxWidth(),
		contentPadding = rememberInsetsPaddingValues(
			LocalWindowInsets.current.statusBars,
			applyBottom = false
		)
	) {
		Box {
			Text(
				modifier = Modifier.fillMaxWidth(),
				textAlign = TextAlign.Center,
				text = stringResource(id = text),
				fontSize = HEADLINE6
			)

			Icon(
				modifier = Modifier
					.align(Alignment.CenterEnd)
					.padding(end = PADDING_MEDIUM)
					.clip(shape = RoundedCornerShape(PADDING_SMALL_MEDIUM))
					.clickable(
						interactionSource = MutableInteractionSource(),
						indication = rememberRipple(bounded = true)
					) {
						navController.navigate(navSettings)
					},
				imageVector = imageVector,
				contentDescription = null
			)
		}
	}
}

@Preview
@Composable
fun MainTopBarPreview() {
	MainTopBar(
		text = R.string.app_name,
		navController = rememberNavController(),
		imageVector = Icons.Rounded.AccountCircle
	)
}
