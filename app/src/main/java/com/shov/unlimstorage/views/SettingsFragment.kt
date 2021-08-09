package com.shov.unlimstorage.views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.shov.unlimstorage.R
import com.shov.unlimstorage.ui.MainTopBar
import com.shov.unlimstorage.values.navMain

@Composable
fun SettingsFragment(navController: NavController) {
	MainTopBar(
		navController = navController,
		nextRoute = null,
		prevRoute = Pair(Icons.Rounded.ArrowBack, navMain),
		text = R.string.settings
	)
}
