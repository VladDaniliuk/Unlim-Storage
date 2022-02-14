package com.shov.unlimstorage.ui.spacers

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.shov.unlimstorage.values.PADDING_FAB
import com.shov.unlimstorage.values.SIZE_FAB

@Composable
fun BottomSheetSpacer() = Spacer(
	modifier = Modifier
		.padding(bottom = 4.dp)
		.navigationBarsWithImePadding()
)

@Composable
fun FabSpacer() = Spacer(
	modifier = Modifier
		.navigationBarsPadding()
		.padding(bottom = SIZE_FAB + PADDING_FAB)
)
