package com.shov.unlimstorage.views

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.shov.unlimstorage.R
import com.shov.unlimstorage.ui.MainTopBar

@Composable
fun MainFragment(navController: NavController) {
	val activity = LocalContext.current as Activity

	BackHandler {
		activity.finish()
	}

	MainTopBar(
		text = R.string.app_name,
		navController = navController,
		imageVector = Icons.Rounded.AccountCircle
	)
}
