package com.shov.unlimstorage.views

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun MainFragment() {
	val activity = LocalContext.current as Activity

	BackHandler {
		activity.finish()
	}
}
