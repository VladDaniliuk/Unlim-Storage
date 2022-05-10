package com.shov.coreui.models

import androidx.compose.ui.graphics.vector.ImageVector

data class TopAppBar(
	val prevRoute: Pair<ImageVector, () -> Unit>? = null,
	val title: String? = null,
	val nextRoute: Pair<ImageVector, () -> Unit>? = null,
	val status: TopAppBarStatus = TopAppBarStatus.Title
)
