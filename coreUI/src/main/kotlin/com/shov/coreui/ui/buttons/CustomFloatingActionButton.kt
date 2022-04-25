package com.shov.coreui.ui.buttons

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.shov.coreui.ui.icons.CustomIcon

@Composable
fun CustomFloatingActionButton(
	modifier: Modifier = Modifier,
	state: CustomFloatingActionButtonState,
	floatingActionButtonModels: List<FloatingActionButtonModel> = emptyList(),
	onClick: () -> Unit
) {
	val transition = updateTransition(targetState = state, label = "FABState")
	val rotation by transition.animateFloat(
		label = "FABRotation"
	) { customFloatingActionButtonState ->
		when (customFloatingActionButtonState) {
			CustomFloatingActionButtonState.Collapsed -> 0f
			CustomFloatingActionButtonState.Expanded -> 45f
		}
	}

	Column(modifier = modifier) {
		floatingActionButtonModels.forEach { floatingActionButtonModel ->
			AnimatedFloatingActionButton(
				state = state,
				text = floatingActionButtonModel.text,
				icon = floatingActionButtonModel.icon,
				onClick = floatingActionButtonModel.onClick,
			)

			Spacer(modifier = Modifier.size(8.dp))
		}

		FloatingActionButton(
			modifier = Modifier.align(Alignment.End),
			onClick = onClick,
		) {
			CustomIcon(
				modifier = Modifier.rotate(rotation),
				imageVector = Icons.Rounded.Add
			)
		}
	}
}

data class FloatingActionButtonModel(
	val icon: ImageVector,
	val text: String,
	val onClick: () -> Unit
)

enum class CustomFloatingActionButtonState { Collapsed, Expanded }
