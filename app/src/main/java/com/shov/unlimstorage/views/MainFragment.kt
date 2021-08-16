package com.shov.unlimstorage.views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.shov.unlimstorage.R
import com.shov.unlimstorage.ui.MainTopBar

@Composable
fun MainFragment(navController: NavController) {
	MainTopBar(
		text = R.string.app_name,
		navController = navController,
		imageVector = Icons.Rounded.AccountCircle
	)
}
