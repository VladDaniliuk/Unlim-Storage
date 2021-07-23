package com.shov.unlimstorage.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shov.unlimstorage.values.CustomTheme
import com.shov.unlimstorage.values.PADDING_MEDIUM
import com.shov.unlimstorage.values.SIGN_IN

@Composable
fun SignInButton(onClick: () -> Unit) {
	CustomTheme {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.fillMaxHeight(),
			verticalArrangement = Arrangement.Bottom,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Button(
				onClick = onClick,
				modifier = Modifier.padding(bottom = PADDING_MEDIUM.dp)
			) {
				Text(text = SIGN_IN)
			}
		}
	}
}