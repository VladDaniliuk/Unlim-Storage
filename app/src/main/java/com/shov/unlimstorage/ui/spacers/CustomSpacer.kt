package com.shov.unlimstorage.ui.spacers

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BottomSheetSpacer() = Spacer(
	modifier = Modifier
		.padding(bottom = 4.dp)
		.navigationBarsPadding()
		.imePadding()
)
