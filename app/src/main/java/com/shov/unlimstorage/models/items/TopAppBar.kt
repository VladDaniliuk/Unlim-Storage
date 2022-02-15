package com.shov.unlimstorage.models.items

import androidx.compose.ui.graphics.vector.ImageVector

data class TopAppBar(
	val prevRoute: Pair<ImageVector, () -> Unit>? = null,
	val title: String? = null,
	val nextRoute: Pair<ImageVector, () -> Unit>? = null
)
