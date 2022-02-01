package com.shov.unlimstorage.ui.dialogs

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.shov.unlimstorage.ui.texts.CustomText

@Composable
fun ColumnScope.CustomHeaderIcon(icon: ImageVector) = Icon(
	contentDescription = icon.name,
	imageVector = icon,
	modifier = Modifier
		.align(Alignment.CenterHorizontally)
		.padding(top = 12.dp)
		.size(48.dp),
	tint = MaterialTheme.colors.onSurface
)

@Composable
fun CustomHeaderText(text: String?) = CustomText(
	modifier = Modifier
		.paddingFromBaseline(top = 40.dp)
		.padding(end = 16.dp),
	text = text,
	textStyle = Typography().h6
)
