package com.shov.coreui.ui.buttons

import androidx.compose.animation.*
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.shov.coreui.ui.icons.CustomIcon
import com.shov.coreui.ui.texts.CustomText

@Composable
fun ColumnScope.AnimatedFloatingActionButton(
	state: CustomFloatingActionButtonState,
	text: String,
	icon: ImageVector,
	onClick: () -> Unit
) {
	@OptIn(ExperimentalAnimationApi::class)
	AnimatedVisibility(
		modifier = Modifier.align(Alignment.End),
		visible = state == CustomFloatingActionButtonState.Expanded,
		enter = fadeIn() + scaleIn(),
		exit = fadeOut() + scaleOut()
	) {
		ExtendedFloatingActionButton(
			text = {
				@OptIn(ExperimentalAnimationApi::class)
				AnimatedVisibility(
					visible = state == CustomFloatingActionButtonState.Expanded,
					enter = fadeIn() + scaleIn(),
					exit = fadeOut() + scaleOut()
				) {
					CustomText(text = text)
				}
			},
			icon = {
				@OptIn(ExperimentalAnimationApi::class)
				AnimatedVisibility(
					visible = state == CustomFloatingActionButtonState.Expanded,
					enter = fadeIn() + scaleIn(),
					exit = fadeOut() + scaleOut()
				) {
					CustomIcon(imageVector = icon)
				}
			},
			onClick = onClick
		)
	}
}