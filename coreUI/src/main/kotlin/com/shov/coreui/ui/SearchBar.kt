package com.shov.coreui.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shov.coreui.models.TopAppBarStatus

@Composable
fun SearchBar(
	status: TopAppBarStatus,
	onClick: () -> Unit,
	content: @Composable RowScope.() -> Unit
) {
	val animatedShape by animateIntAsState(if (status == TopAppBarStatus.PreSearch) 50 else 0)
	val animatedStatusBarPadding by animateDpAsState(
		if (status == TopAppBarStatus.PreSearch) WindowInsets.statusBars.asPaddingValues()
			.calculateTopPadding() else 0.dp
	)
	val animatedVerticalPadding by animateDpAsState(
		if (status == TopAppBarStatus.PreSearch) 8.dp else 0.dp
	)
	val animatedHorizontalPadding by animateDpAsState(
		if (status == TopAppBarStatus.PreSearch) 16.dp else 0.dp
	)
	val animatedHeight by animateDpAsState(
		if (status == TopAppBarStatus.PreSearch) 48.dp else 64.dp
	)

	Row(
		modifier = Modifier
			.padding(top = animatedStatusBarPadding)
			.padding(
				horizontal = animatedHorizontalPadding, vertical = animatedVerticalPadding
			)
			.clip(shape = RoundedCornerShape(animatedShape))
			.background(
				TopAppBarDefaults
					.centerAlignedTopAppBarColors()
					.containerColor(1f).value
			)
			.clickable(
				onClick = { if (status == TopAppBarStatus.PreSearch) onClick() },
				indication = if (status == TopAppBarStatus.PreSearch)
					LocalIndication.current else null,
				interactionSource = remember { MutableInteractionSource() })
			.padding(
				top = WindowInsets.statusBars
					.asPaddingValues()
					.calculateTopPadding() - animatedStatusBarPadding,
			)
			.height(animatedHeight)
			.fillMaxWidth(),
		content = content
	)
}

@Preview
@Composable
fun SearchBarPreview() {
	SearchBar(TopAppBarStatus.Search, {}, {})
}
